package com.sommor.bundles.qrcode.repository;

import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
@Mapper
public interface QrCodeRepository extends CurdRepository<QrCodeEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    QrCodeEntity findByCode(String code);
}
