package com.sommor.bundles.wine.worldwine;

import com.sommor.bundles.wine.jiukacha.JiuKaChaService;
import com.sommor.core.spring.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */
public class WorldWineListPipeline implements Pipeline {

    private JiuKaChaService jiuKaChaService = ApplicationContextHolder.getBean(JiuKaChaService.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        WorldWineListItemResult result = resultItems.get("WorldWineListItemResult");
        if (null != result && StringUtils.isNotBlank(result.getSubTitle())) {

        }

    }
}
