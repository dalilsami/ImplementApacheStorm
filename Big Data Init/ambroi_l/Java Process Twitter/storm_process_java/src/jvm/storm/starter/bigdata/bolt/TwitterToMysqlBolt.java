package storm.starter.bigdata.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import storm.starter.bigdata.util.MyProperties;
import twitter4j.Status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public  class TwitterToMysqlBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 42L;

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet"));
    }

    public void execute(Tuple input, BasicOutputCollector collector)
    {

        Status status = (Status) input.getValueByField("tweet");
        if(status != null) {


            SimpleDateFormat cet =
                    new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss zzz");

            cet.setTimeZone(TimeZone.getTimeZone("CET"));

            SimpleDateFormat utc =
                    new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss zzz");

            utc.setTimeZone(TimeZone.getTimeZone("UTC"));
            double longitude = 0; double latitude = 0;
            String placeGeometryType = ""; String placeCountry = ""; String placeCountryCode = ""; String placeName = "";
            String placeFullName = ""; String placeId = ""; String placeUrl = ""; Long retweet_id =0L ;
            if(status.getGeoLocation() != null) {
                 longitude = status.getGeoLocation().getLongitude();
                 latitude = status.getGeoLocation().getLatitude();
            }
            else {
                 longitude = 0;
                 latitude = 0;
            }
            if(status.getPlace() != null)
            {
               placeGeometryType = status.getPlace().getGeometryType();
               placeCountry  = status.getPlace().getCountry();
                placeCountryCode =status.getPlace().getCountryCode();
                placeName =status.getPlace().getName();
                placeFullName =status.getPlace().getFullName();
                placeId =status.getPlace().getId();
                placeUrl =status.getPlace().getURL();
            }
            if(status.getRetweetedStatus() != null)
                retweet_id = status.getRetweetedStatus().getId();

            if ("0".compareTo(Long.toString(retweet_id)) == 0 && status.getUser().getFriendsCount() >= 1000 && status.getUser().getFavouritesCount() >= 1000 && ("en".compareTo(status.getUser().getLang()) == 0 || "fr".compareTo(status.getUser().getLang()) == 0)) {
                String sql = "INSERT INTO  `bigdata`.`Influence_tweet` (tweet_author_id, tweet_author_name, tweet_author_screen_name, tweet_author_friends_count,"
                        + "tweet_author_location, tweet_id, tweet_text, tweet_created_at, tweet_retweet_count,  tweet_author_favorites_count, tweet_favorited)"
                        + "VALUES(\"" + status.getUser().getId() + "\", "
                        + "\"" + status.getUser().getName() + "\", "
                        + "\"" + status.getUser().getScreenName() + "\", "
                        + "\"" + status.getUser().getFriendsCount() + "\", "
                        + "\"" + status.getUser().getLocation() + "\", "
                        + "\"" + status.getId() + "\", "
                        + "\"" + status.getText().replaceAll("\"", "''") + "\", "
                        + "\"" + status.getCreatedAt() + "\", "
                        + "\"" + status.getRetweetCount() + "\", "
                        + "\"" + status.getUser().getFavouritesCount() + "\", "
                        + "\"" + status.isFavorited() + "\") ";
                this.insertDB(sql.replaceAll("'", "''"));
                collector.emit(new Values(status));
            }
        }
    }

    /**
     * Play a request on MySQL Database
     * @param sql
     */
    private void insertDB(String sql)
    {
        String url = MyProperties.getProperties("mysql_string");
        String username = MyProperties.getProperties("mysql_user");
        String password = MyProperties.getProperties("mysql_password");

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            Statement st = (Statement) connection.createStatement();

            st.executeUpdate(sql);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while executing request : "+sql + "\n "+e.getMessage());
        }
    }


    public Map<String, Object> getComponentConfiguration() { return null; }
}