package com.techbloghub.common.config;

import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssReaderConfig {

  @Bean
  public RssReader rssReader() {
    return new RssReader();
  }

}
