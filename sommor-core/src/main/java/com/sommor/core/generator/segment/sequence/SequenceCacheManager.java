package com.sommor.core.generator.segment.sequence;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public class SequenceCacheManager {

    private static final Map<String, SequenceCache> MAP = new HashMap<>();

    private List<SequenceRepository> sequenceRepositories;

    @Getter
    private int dbSize;

    private Random random = new Random();

    public SequenceCacheManager(List<SequenceRepository> sequenceRepositories) {
        this.sequenceRepositories = sequenceRepositories;
        this.dbSize = sequenceRepositories.size();
    }

    public SequenceCache getSequenceCache(SequenceId sequenceId) {
        String name = sequenceId.getName();
        SequenceCache sequenceCache = MAP.get(name);
        if (needRefresh(sequenceCache)) {
            synchronized (this) {
                sequenceCache = MAP.get(name);
                if (needRefresh(sequenceCache)) {
                    int dbIndex = dbSize == 1 ? 0 : random.nextInt(dbSize);
                    SequenceRepository sequenceRepository = sequenceRepositories.get(dbIndex);
                    long prefix = parsePrefix(sequenceId.getName(), sequenceCache, sequenceRepository);
                    sequenceCache = sequenceRepository.updateSequence(sequenceId, prefix);
                    MAP.put(name, sequenceCache);
                }
            }
        }

        return sequenceCache;
    }

    private long parsePrefix(String name, SequenceCache sequenceCache, SequenceRepository sequenceRepository) {
        return 1;
    }

    private boolean needRefresh(SequenceCache sequenceCache) {
        if (null == sequenceCache || ! sequenceCache.hasNext()) {
            return true;
        }

        return false;
    }

}
