package com.wxfw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * PropertyReader
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class PropertyReader {

    private File target;
    private Map<String, Object> allProps;

    public PropertyReader(File target) {
        this.target = target;
        this.allProps = new HashMap<String, Object>();
    }

    public Map<String, Object> read() {
        if (target != null && target.exists()) {
            readForder(target);
        }
        return allProps;
    }

    public void readForder(File propsForder) {
        if (propsForder.exists()) {
            File[] files = propsForder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".properties")) {
                        load(file);
                    }
                } else {
                    readForder(file);
                }
            }
        }
    }

    private void load(File propFile) {
        InputStream is = null;
        try {
            is = new FileInputStream(propFile);
            Properties properties = new Properties();
            properties.load(is);
            Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                allProps.put(entry.getKey().toString(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
