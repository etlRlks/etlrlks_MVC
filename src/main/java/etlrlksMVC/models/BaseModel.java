package etlrlksMVC.models;

import java.lang.reflect.Field;

public class BaseModel {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        

        sb.append("(");
        sb.append(this.getClass().getSimpleName() + ":\n");
        Field[] fields = this.getClass().getFields();
        for (Field f:fields) {
            try {
                Object v = f.get(this);
                String s = String.format("%s: %s\n", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        sb.append(")");
        return sb.toString();
    }
}
