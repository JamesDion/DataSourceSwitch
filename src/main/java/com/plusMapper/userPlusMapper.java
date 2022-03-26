package com.plusMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusEntity.Authic;

import java.util.List;

public interface userPlusMapper extends BaseMapper<Authic> {
    /**
     * 对应xml
     *
     * @return
     */
    public List<Authic> getPlusUser();
}
