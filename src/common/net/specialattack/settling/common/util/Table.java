
package net.specialattack.settling.common.util;

import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

public class Table<K, V, W> {

    private final TreeMap<K, Value<V, W>> entries;

    public Table() {
        entries = new TreeMap<K, Value<V, W>>();
    }

    public int size() {
        return entries.size();
    }

    public void insert(K key, V value1, W value2) {
        entries.put(key, new Value<V, W>(value1, value2));
    }

    public boolean containsKey(K key) {
        return entries.containsKey(key);
    }

    public boolean containsValue1(V value1) {
        for (K key : entries.keySet()) {
            if (entries.get(key).getValue1().equals(value1)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue2(W value2) {
        for (K key : entries.keySet()) {
            if (entries.get(key).getValue2().equals(value2)) {
                return true;
            }
        }

        return false;
    }

    public Value<V, W> deleteEntry(K key) {
        return entries.remove(key);
    }

    public Value<V, W> getValue(K key) {
        return entries.get(key);
    }

    public V getValue1(K key) {
        return entries.get(key).getValue1();
    }

    public W getValue2(K key) {
        return entries.get(key).getValue2();
    }

    public K getKey1(V value1) {
        for (K key : entries.keySet()) {
            if (entries.get(key).getValue1().equals(value1)) {
                return key;
            }
        }

        return null;
    }

    public K getKey2(W value2) {
        for (K key : entries.keySet()) {
            if (entries.get(key).getValue2().equals(value2)) {
                return key;
            }
        }

        return null;
    }

    public Collection<Entry<K, V, W>> getEntries() {
        TreeSet<Entry<K, V, W>> result = new TreeSet<Entry<K, V, W>>();

        for (java.util.Map.Entry<K, Value<V, W>> entry : entries.entrySet()) {
            result.add(new Entry<K, V, W>(entry.getKey(), entry.getValue().value1, entry.getValue().value2));
        }

        return result;
    }

    public static class Value<V, W> {

        private final V value1;
        private final W value2;

        Value(V value1, W value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public V getValue1() {
            return value1;
        }

        public W getValue2() {
            return value2;
        }

    }

    public static class Entry<K, V, W> {

        private final K key;
        private final V value1;
        private final W value2;

        Entry(K key, V value1, W value2) {
            this.key = key;
            this.value1 = value1;
            this.value2 = value2;
        }

        public K getKey() {
            return key;
        }

        public V getValue1() {
            return value1;
        }

        public W getValue2() {
            return value2;
        }

    }

}
