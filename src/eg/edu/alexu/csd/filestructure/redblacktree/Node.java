package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node<T extends Comparable<T>, V> implements INode<T, V> {
    INode<T, V> parent;
    INode<T, V> leftChild;
    INode<T, V> rightChild;
    T key;
    V value;
    boolean color;

    public Node(T key, V value, INode<T, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        //node is created with default color red
        this.color = INode.RED;
        //construct children as tail nodes
        this.leftChild = new Node<T, V>(this);
        this.rightChild = new Node<T, V>(this);
    }

    public Node(INode<T, V> parent) {
        this.parent = parent;
        //null node is created with Black color ( tail )
        this.color = INode.BLACK;
    }

    @Override
    public void setParent(INode<T, V> parent) {
        this.parent = parent;
    }

    @Override
    public INode<T, V> getParent() {
        return this.parent;
    }

    @Override
    public void setLeftChild(INode<T, V> leftChild) {
        this.leftChild = leftChild;
    }

    @Override
    public INode<T, V> getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public INode<T, V> getRightChild() {
        return this.rightChild;
    }

    @Override
    public T getKey() {
        return this.key;
    }

    @Override
    public void setKey(T key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean getColor() {
        return this.color;
    }

    @Override
    public void setColor(boolean color) {
        this.color = color;
    }

    @Override
    public boolean isNull() {
        return this.key == null;
    }
}
