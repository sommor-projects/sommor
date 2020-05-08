package com.sommor.core.component.bytes;

import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.extensibility.config.Implement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Implement
public class BytesConvertProcessor implements FieldFillProcessor<BytesConvertConfig> {

    @Override
    public Object processOnFieldFill(BytesConvertConfig config, FieldFillContext ctx) {
        Long v;
        if (StringUtils.isBlank(config.getField())) {
            v = ctx.getSourceModelFieldValue();
        } else {
            v = ctx.getSourceModel().getFieldValue(config.getField());
        }

        if (null != v) {
            ByteUnit sourceUnit = config.getSourceUnit();
            if (Boolean.TRUE.equals(config.getAutoUnit())) {
                double value = v;
                ByteUnit byteUnit = sourceUnit;

                for (ByteUnit unit : ByteUnit.values()) {
                    if (unit.getLevel() > sourceUnit.getLevel()) {
                        value = value / 1024;
                        if (value > 0 && value < 1024) {
                            byteUnit = unit;
                            break;
                        }
                    }
                }

                return String.format("%.2f %s", value, byteUnit.name());
            } else {
                ByteUnit targetUnit = config.getTargetUnit();
                int diff = targetUnit.getLevel() - sourceUnit.getLevel();
                if (0 == diff) {
                    return v;
                } else if (v > 0) {
                    double value = v;
                    while (diff > 0) {
                        value = value / 1024;
                        diff--;
                    }
                    return String.format("%.2f %s", value, targetUnit.name());
                } else if (v < 0) {
                    long value = v;
                    while (diff > 0) {
                        value = value * 1024;
                        diff++;
                    }
                    return value + " " + targetUnit.getLevel();
                }
            }
        }

        return null;
    }
}
