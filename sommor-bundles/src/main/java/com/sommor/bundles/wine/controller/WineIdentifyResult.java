package com.sommor.bundles.wine.controller;

import com.sommor.bundles.wine.jiukacha.JiuKaChaWineResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Getter
@Setter
public class WineIdentifyResult {

    private boolean exactMatched;

    private List<JiuKaChaWineResult> wineResults;

}
