package com.dogne.ultimate2;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class IndexMap implements Rule,Cloneable
{
    public static final int  ALL_OUTER_INDEX = -1;
    public static final int  RANDOM_OUTER_INDEX = -2;

    private HashMap<Integer,Integer> indexMap;

    public IndexMap(Dimension dimension)
    {
        indexMap = new HashMap<>();

        for(int innerIndex = 0; innerIndex < dimension.getInnerLength() ; innerIndex++)
            add(innerIndex,innerIndex);
    }

    public IndexMap(IndexMap other)
    {
        this.indexMap = (HashMap<Integer, Integer>) other.indexMap.clone();
    }

    @NonNull
    @Override
    protected IndexMap clone()
    {
        return new IndexMap(this);
    }

    public void add(int innerIndex, int outerIndex)
    {
        indexMap.put(innerIndex,outerIndex);
    }

    public HashMap<Integer, Integer> getIndexMap()
    {
        return indexMap;
    }
}
