package giraph.api;

import java.io.IOException;


import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.io.EdgeReader;
import org.apache.giraph.io.formats.TextEdgeInputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class SimpleEdgeInputFormat extends
			TextEdgeInputFormat<Text, Text> {
	
	@Override
	public EdgeReader<Text, Text> createEdgeReader(
			InputSplit split, TaskAttemptContext context) throws IOException {
		return new SimpleTextEdgeReader();
	}
	
	public class SimpleTextEdgeReader extends TextEdgeReader {
		private RecordReader<LongWritable, Text> lineRecordReader;
		@Override
		public void initialize(InputSplit inputSplit,
			TaskAttemptContext context)
		throws IOException, InterruptedException {

			lineRecordReader = new LineRecordReader();
			lineRecordReader.initialize(inputSplit, context);
		}
		
		@Override
		public final boolean nextEdge()
		throws IOException, InterruptedException {
			return lineRecordReader.nextKeyValue();
		}
		@Override
		public final Text getCurrentSourceId() throws IOException,
		InterruptedException {
			Text line = getRecordReader().getCurrentValue();
			String[] words = line.toString().split(" ");
			return new Text(words[0]);
		}
		@Override
		public final Edge<Text, Text> getCurrentEdge()
		throws IOException, InterruptedException {
			Text line = getRecordReader().getCurrentValue();
			String[] words = line.toString().split(" ");
			Text targetVertexId = new Text(words[1]);
			Text edgeValue = new Text(words[2]);
			return EdgeFactory.create(targetVertexId, edgeValue);
			
		}

	}

}