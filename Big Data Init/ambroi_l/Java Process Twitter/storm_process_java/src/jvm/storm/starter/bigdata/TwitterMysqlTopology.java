package storm.starter.bigdata;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;
import storm.starter.bigdata.bolt.TwitterToMysqlBolt;
import storm.starter.bigdata.spout.TwitterSpout;
import storm.starter.bigdata.util.MyProperties;

public class TwitterMysqlTopology {



    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("twitterinput", new TwitterSpout(MyProperties.getProperties("twitter_consumer_key"),
                MyProperties.getProperties("twitter_consumer_secret"),
                MyProperties.getProperties("twitter_consumer_access_token"),
                MyProperties.getProperties("twitter_consumer_access_token_secret")));
        builder.setBolt("push", new TwitterToMysqlBolt(), 2).shuffleGrouping("twitterinput");

        Config conf = new Config();
        conf.setDebug(false);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(1);

            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        }
        else {

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("twitterfilter", conf, builder.createTopology());
            Utils.sleep(640000);
            cluster.killTopology("twitterfilter");
            cluster.shutdown();
        }
    }
}
