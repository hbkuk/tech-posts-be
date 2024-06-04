package com.techbloghub.common.util;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtil {

    public static String getFilteredContent(String content) {
        String filteredContent = content.replaceAll("(?s)<script[^>]*>.*?</script>", "");
        return StringEscapeUtils.unescapeHtml4(filteredContent).replaceAll("<[^>]*>", "");
    }
}
