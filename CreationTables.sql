--
-- Database: `bigdata`
--

CREATE DATABASE IF NOT EXISTS bigdata;

-- --------------------------------------------------------

USE bigdata;

--
-- Table structure for table `Author_tweet`
--

CREATE TABLE IF NOT EXISTS `Influence_tweet` (
  `tweet_author_id` longtext NOT NULL,
  `tweet_author_name` longtext NOT NULL,
  `tweet_author_screen_name` longtext NOT NULL,
  `tweet_author_friends_count` longtext NOT NULL,
  `tweet_author_location` longtext NOT NULL,
  `tweet_id` longtext NOT NULL,
  `tweet_text` longtext NOT NULL,
  `tweet_created_at` longtext NOT NULL,
  `tweet_retweet_count` longtext NOT NULL,
  `tweet_favorited` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
