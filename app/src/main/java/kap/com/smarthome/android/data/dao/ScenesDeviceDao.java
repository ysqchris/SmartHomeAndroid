package kap.com.smarthome.android.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import kap.com.smarthome.android.data.bean.ScenesDevice;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCENES_DEVICE".
*/
public class ScenesDeviceDao extends AbstractDao<ScenesDevice, Long> {

    public static final String TABLENAME = "SCENES_DEVICE";

    /**
     * Properties of entity ScenesDevice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property GUID = new Property(1, String.class, "GUID", false, "GUID");
        public final static Property SCENES_ID = new Property(2, String.class, "SCENES_ID", false, "SCENES__ID");
        public final static Property DEVICE_ID = new Property(3, String.class, "DEVICE_ID", false, "DEVICE__ID");
        public final static Property DEVICE_NAME = new Property(4, String.class, "DEVICE_NAME", false, "DEVICE__NAME");
        public final static Property DEVICE_INFO = new Property(5, String.class, "DEVICE_INFO", false, "DEVICE__INFO");
        public final static Property DEVICE_VALUE = new Property(6, String.class, "DEVICE_VALUE", false, "DEVICE__VALUE");
        public final static Property RELAYBOX_ID = new Property(7, String.class, "RELAYBOX_ID", false, "RELAYBOX__ID");
        public final static Property TYPE = new Property(8, int.class, "TYPE", false, "TYPE");
    }


    public ScenesDeviceDao(DaoConfig config) {
        super(config);
    }
    
    public ScenesDeviceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCENES_DEVICE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"GUID\" TEXT," + // 1: GUID
                "\"SCENES__ID\" TEXT," + // 2: SCENES_ID
                "\"DEVICE__ID\" TEXT," + // 3: DEVICE_ID
                "\"DEVICE__NAME\" TEXT," + // 4: DEVICE_NAME
                "\"DEVICE__INFO\" TEXT," + // 5: DEVICE_INFO
                "\"DEVICE__VALUE\" TEXT," + // 6: DEVICE_VALUE
                "\"RELAYBOX__ID\" TEXT," + // 7: RELAYBOX_ID
                "\"TYPE\" INTEGER NOT NULL );"); // 8: TYPE
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCENES_DEVICE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ScenesDevice entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String GUID = entity.getGUID();
        if (GUID != null) {
            stmt.bindString(2, GUID);
        }
 
        String SCENES_ID = entity.getSCENES_ID();
        if (SCENES_ID != null) {
            stmt.bindString(3, SCENES_ID);
        }
 
        String DEVICE_ID = entity.getDEVICE_ID();
        if (DEVICE_ID != null) {
            stmt.bindString(4, DEVICE_ID);
        }
 
        String DEVICE_NAME = entity.getDEVICE_NAME();
        if (DEVICE_NAME != null) {
            stmt.bindString(5, DEVICE_NAME);
        }
 
        String DEVICE_INFO = entity.getDEVICE_INFO();
        if (DEVICE_INFO != null) {
            stmt.bindString(6, DEVICE_INFO);
        }
 
        String DEVICE_VALUE = entity.getDEVICE_VALUE();
        if (DEVICE_VALUE != null) {
            stmt.bindString(7, DEVICE_VALUE);
        }
 
        String RELAYBOX_ID = entity.getRELAYBOX_ID();
        if (RELAYBOX_ID != null) {
            stmt.bindString(8, RELAYBOX_ID);
        }
        stmt.bindLong(9, entity.getTYPE());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ScenesDevice entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String GUID = entity.getGUID();
        if (GUID != null) {
            stmt.bindString(2, GUID);
        }
 
        String SCENES_ID = entity.getSCENES_ID();
        if (SCENES_ID != null) {
            stmt.bindString(3, SCENES_ID);
        }
 
        String DEVICE_ID = entity.getDEVICE_ID();
        if (DEVICE_ID != null) {
            stmt.bindString(4, DEVICE_ID);
        }
 
        String DEVICE_NAME = entity.getDEVICE_NAME();
        if (DEVICE_NAME != null) {
            stmt.bindString(5, DEVICE_NAME);
        }
 
        String DEVICE_INFO = entity.getDEVICE_INFO();
        if (DEVICE_INFO != null) {
            stmt.bindString(6, DEVICE_INFO);
        }
 
        String DEVICE_VALUE = entity.getDEVICE_VALUE();
        if (DEVICE_VALUE != null) {
            stmt.bindString(7, DEVICE_VALUE);
        }
 
        String RELAYBOX_ID = entity.getRELAYBOX_ID();
        if (RELAYBOX_ID != null) {
            stmt.bindString(8, RELAYBOX_ID);
        }
        stmt.bindLong(9, entity.getTYPE());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ScenesDevice readEntity(Cursor cursor, int offset) {
        ScenesDevice entity = new ScenesDevice( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // GUID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SCENES_ID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // DEVICE_ID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // DEVICE_NAME
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // DEVICE_INFO
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // DEVICE_VALUE
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // RELAYBOX_ID
            cursor.getInt(offset + 8) // TYPE
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ScenesDevice entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSCENES_ID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDEVICE_ID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDEVICE_NAME(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDEVICE_INFO(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDEVICE_VALUE(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRELAYBOX_ID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTYPE(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ScenesDevice entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ScenesDevice entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ScenesDevice entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
