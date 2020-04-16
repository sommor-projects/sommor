package com.sommor.bundles.mall.shop.repository;

import com.sommor.bundles.search.entity.SubjectIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public interface ShopIndexRepository extends ElasticsearchRepository<SubjectIndex, Long> {
}
