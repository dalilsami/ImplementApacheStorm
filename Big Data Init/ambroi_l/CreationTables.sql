--
-- Database: `bigdata`
--

CREATE DATABASE IF NOT EXISTS bigdata;

-- --------------------------------------------------------

USE bigdata;

--
-- Table structure for table `Author_tweet`
--

CREATE TABLE IF NOT EXISTS `Author_tweet` (
  `tweet_author_id` longtext NOT NULL,
  `tweet_author_name` longtext NOT NULL,
  `tweet_author_screen_name` longtext NOT NULL,
  `tweet_author_created_at` longtext NOT NULL,
  `tweet_author_friends_count` longtext NOT NULL,
  `tweet_author_location` longtext NOT NULL,
  `tweet_author_time_zone` longtext NOT NULL,
  `tweet_id` longtext NOT NULL,
  `tweet_text` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Tweet_impact`
--

CREATE TABLE IF NOT EXISTS `Tweet_impact` (
  `tweet_id` longtext NOT NULL,
  `tweet_text` longtext NOT NULL,
  `tweet_in_reply_to_tweet` longtext NOT NULL,
  `tweet_retweeted` longtext NOT NULL,
  `tweet_retweet_count` longtext NOT NULL,
  `tweet_favorited` longtext NOT NULL,
  `tweet_author_protected` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Tweet_propreties`
--

CREATE TABLE IF NOT EXISTS `Tweet_propreties` (
  `tweet_id` longtext NOT NULL,
  `tweet_text` longtext NOT NULL,
  `tweet_author_id` longtext NOT NULL,
  `tweet_author_screen_name` longtext NOT NULL,
  `tweet_created_at_str_cet` longtext NOT NULL,
  `tweet_source` longtext NOT NULL,
  `tweet_lang` longtext NOT NULL,
  `tweet_place_country` longtext NOT NULL,
  `tweet_place_name` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
