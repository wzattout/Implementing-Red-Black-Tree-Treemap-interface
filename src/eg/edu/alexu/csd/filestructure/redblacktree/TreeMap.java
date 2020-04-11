package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;
import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

    RedBlackTree map = new RedBlackTree();
    int size = 0;

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (key == null || size == 0)
            throw new RuntimeErrorException(new Error());
        ArrayList<Map.Entry<T, V>> s = new ArrayList<>();
        s = headMap(lastKey(), true);
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).getKey().equals(key)) {
                return Map.entry(s.get(i).getKey(), s.get(i).getValue());
            }
        }
        return null;
    }

    @Override
    public T ceilingKey(T key) {
        return ceilingEntry(key).getKey();
    }

    @Override
    public void clear() {
        map.clear();
        size = 0;
    }

    @Override
    public boolean containsKey(T key) {
        return map.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        if (value == null)
            throw new RuntimeErrorException(new Error());
        Collection<V> c = values();
        return c.contains(value);
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        Set<Map.Entry<T, V>> s = new LinkedHashSet<>();
        return inOrderTraversal(s, map.getRoot());
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if (size == 0)
            return null;
        INode<T, V> node = map.getRoot();
        while (!node.getLeftChild().isNull())
            node = node.getLeftChild();
        return Map.entry(node.getKey(), node.getValue());
    }

    @Override
    public T firstKey() {
        if (size == 0)
            return null;
        return firstEntry().getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        if (key == null || size == 0)
            throw new RuntimeErrorException(new Error());
        ArrayList<Map.Entry<T, V>> s = new ArrayList<>();
        s = headMap(lastKey(), true);
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).getKey().equals(key)) {
                return Map.entry(s.get(i).getKey(), s.get(i).getValue());
            }
        }
        return null;
    }

    @Override
    public T floorKey(T key) {
        return floorEntry(key).getKey();
    }

    @Override
    public V get(T key) {
        return (V) map.search(key);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        return headMap(toKey, false);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        ArrayList<Map.Entry<T, V>> s = new ArrayList<>();
        return inOrderTraversal(s, map.getRoot(), toKey, inclusive);
    }

    @Override
    public Set<T> keySet() {
        Set<T> s = new LinkedHashSet<>();
        return keyInOrderTraversal(s, map.getRoot());
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        if (size == 0)
            return null;
        INode<T, V> node = map.getRoot();
        while (!node.getRightChild().isNull())
            node = node.getRightChild();
        return Map.entry(node.getKey(), node.getValue());
    }

    @Override
    public T lastKey() {
        if (size == 0)
            return null;
        return lastEntry().getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        Map.Entry<T, V> firstEntry = firstEntry();
        if (firstEntry == null)
            return null;
        remove(firstEntry.getKey());
        return firstEntry;
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        Map.Entry<T, V> lastEntry = lastEntry();
        if (lastEntry == null)
            return null;
        remove(lastEntry.getKey());
        return lastEntry;
    }

    @Override
    public void put(T key, V value) {
        if (!map.contains(key))
            size++;
        map.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if (map == null)
            throw new RuntimeErrorException(new Error());
        for (Map.Entry<T, V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean remove(T key) {
        size--;
        return map.delete(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Collection<V> c = new ArrayList<>();
        Set<Map.Entry<T, V>> s = new HashSet<>();
        s = entrySet();
        for (Map.Entry<T, V> entry : s) {
            c.add(entry.getValue());
        }
        return c;
    }

    private Set<Map.Entry<T, V>> inOrderTraversal(Set<Map.Entry<T, V>> s, INode<T, V> node) {
        if (node.isNull())
            return s;
        inOrderTraversal(s, node.getLeftChild());
        s.add(Map.entry(node.getKey(), node.getValue()));
        inOrderTraversal(s, node.getRightChild());
        return s;
    }

    private ArrayList<Map.Entry<T, V>> inOrderTraversal(ArrayList<Map.Entry<T, V>> s, INode<T, V> node, T key, boolean inclusive) {
        if (node == null || node.isNull())
            return s;
        inOrderTraversal(s, node.getLeftChild(), key, inclusive);
        if (node.getKey().compareTo(key) < 0)
            s.add(Map.entry(node.getKey(), node.getValue()));
        else if (node.getKey().equals(key) && inclusive)
            s.add(Map.entry(node.getKey(), node.getValue()));
        inOrderTraversal(s, node.getRightChild(), key, inclusive);
        return s;
    }

    private Set<T> keyInOrderTraversal(Set<T> s, INode<T, V> node) {
        if (node.isNull())
            return s;
        keyInOrderTraversal(s, node.getLeftChild());
        s.add((T) node.getKey());
        keyInOrderTraversal(s, node.getRightChild());
        return s;
    }
}
