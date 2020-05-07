package com.base.sort;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class MetaData implements Comparable<MetaData> {
    int index;

    int gdOrder;

    @Override
    public String toString() {
        return index + "@" + gdOrder;
    }


    @Override
    public int compareTo(MetaData o) {
        if (this.getGdOrder() == o.getGdOrder()) {
            return 0;
        } else if (this.getGdOrder() > o.getGdOrder()) {
            return 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        Collections.addAll(list,3,4,2,1);
        System.out.println("排序前:"+list);
        Collections.sort(list);
        System.out.println("排序后:"+list);
    }
}
