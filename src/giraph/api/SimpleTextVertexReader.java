package giraph.api;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.giraph.edge.DefaultEdge;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.VertexInputFormat;
import org.apache.giraph.io.VertexReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;


public class SimpleTextVertexReader extends VertexInputFormat<Text, IntWritable, IntWritable>
{
	@Override
	public VertexReader<Text, IntWritable, IntWritable> createVertexReader(
			InputSplit arg0, TaskAttemptContext arg1) throws IOException {
		// TODO Auto-generated method stub
		return new SimpleTextVertexR();
	}
	public class SimpleTextVertexR
	extends VertexReader<Text, IntWritable, IntWritable> {

		private RecordReader<LongWritable, Text> lineRecordReader;
		@SuppressWarnings("unused")
		private TaskAttemptContext context;

		@Override
		public void initialize(InputSplit inputSplit, TaskAttemptContext context)
		throws IOException, InterruptedException {
			this.context = context;
			lineRecordReader = new LineRecordReader();
			lineRecordReader.initialize(inputSplit, context);
		}

		@Override
		public final boolean nextVertex()
		throws IOException, InterruptedException {
			return lineRecordReader.nextKeyValue();
		}

		@Override
		public final Vertex<Text, IntWritable, IntWritable> getCurrentVertex()
		throws IOException, InterruptedException {

			Text line = lineRecordReader.getCurrentValue();
			Vertex<Text, IntWritable, IntWritable> vertex =
			getConf().createVertex();
			String[] words = line.toString().split(" ");
			Text id = new Text(words[0]);
			IntWritable age = new IntWritable(Integer.parseInt(words[1]));

			List<Edge<Text, IntWritable>> edges = new ArrayList<Edge<Text, IntWritable>>();
			for (int n = 2; n < words.length; n=n+2) {
				Text destId = new Text(words[n]);
				IntWritable numMentions =
				new IntWritable(Integer.parseInt(words[n+1]));
				DefaultEdge<Text, IntWritable> edge = new DefaultEdge<Text,IntWritable>();
				edge.setTargetVertexId(destId);
				edge.setValue(numMentions);
				}
				vertex.initialize(id, age, edges);
				return vertex;
			}

			@Override
			public void close() throws IOException {
				lineRecordReader.close();
			}

			@Override
			public float getProgress() throws IOException, InterruptedException {
				// TODO Auto-generated method stub
				return 0;
			}
		}
	@Override
	public void checkInputSpecs(Configuration arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<InputSplit> getSplits(JobContext arg0, int arg1)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
