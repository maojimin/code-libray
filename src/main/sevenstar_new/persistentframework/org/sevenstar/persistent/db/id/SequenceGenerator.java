package org.sevenstar.persistent.db.id;

import java.io.Serializable;
import java.util.Map;
import org.sevenstar.persistent.db.ibatis.IbatisDao;

public class SequenceGenerator
  implements IDGenerator
{
  public Serializable generate(Map map)
  {
    Class klass = (Class)map.get(map.keySet().toArray()[0]);
    return IbatisDao.getDao().getIdValue(klass.getSimpleName() + "_base_selectseq");
  }
}