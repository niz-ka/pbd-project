CREATE EXTERNAL TABLE IF NOT EXISTS collisions_ext(
 street STRING,
 zip_code STRING,
 victim_type STRING,
 injuries_type STRING,
 value INT)
 COMMENT 'collisions in NYC'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY '|'
 LINES TERMINATED BY '\n'
 STORED AS SEQUENCEFILE
 location '${input_dir3}';

 CREATE EXTERNAL TABLE IF NOT EXISTS boroughs_ext(
 zip_code STRING,
 borough STRING)
 COMMENT 'boroughs and zip codes'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 LINES TERMINATED BY '\n'
 STORED AS TEXTFILE
 location '${input_dir4}'
 TBLPROPERTIES ("skip.header.line.count"="1");

 CREATE EXTERNAL TABLE IF NOT EXISTS result_ext(
 street STRING,
 person_type STRING,
 injured INT,
 killed INT)
 COMMENT 'analysis result'
 ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.JsonSerDe'
 STORED AS TEXTFILE
 location '${output_dir6}';

 INSERT OVERWRITE TABLE result_ext(
    SELECT street, person_type, injured, killed 
    FROM(
        SELECT street, 
        victim_type AS person_type, 
        sum(CASE WHEN injuries_type = 'INJURED' THEN value ELSE 0 END) AS injured, 
        sum(CASE WHEN injuries_type = 'KILLED' THEN value ELSE 0 END) AS killed, 
        row_number() over (partition by victim_type order by sum(value) desc) as rank 
        FROM collisions_ext 
        INNER JOIN boroughs_ext on collisions_ext.zip_code = boroughs_ext.zip_code 
        WHERE borough = 'MANHATTAN' 
        GROUP BY street, victim_type 
        ORDER BY victim_type, sum(value) DESC) t WHERE rank <= 3
    );