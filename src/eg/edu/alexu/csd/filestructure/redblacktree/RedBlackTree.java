package eg.edu.alexu.csd.filestructure.redblacktree;


import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {
    INode<T, V> root;
    public RedBlackTree(){
        root=new Node<T,V>(null);
    }
    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return (root==null || root.isNull());
    }

    @Override
    public void clear() {
        root=new Node<T,V>(null);
    }

    @Override
    public V search(T key) {
        if( key == null )
            throw new RuntimeErrorException(new Error());

        INode<T, V> node=root;
        while ( !node.isNull()  &&  node.getKey().compareTo(key) != 0){
            if(node.getKey().compareTo(key) < 0)
                node=node.getRightChild();
            else
                node=node.getLeftChild();
        }
        //either you reach the node or some tail
        return node.getValue();
    }

    @Override
    public boolean contains(T key) {
        if( key == null )
            throw new RuntimeErrorException(new Error());
        INode<T, V> node=root;
        while ( !node.isNull()  &&  node.getKey().compareTo(key) != 0){
            if(node.getKey().compareTo(key) < 0)
                node=node.getRightChild();
            else
                node=node.getLeftChild();
        }
        if(!node.isNull() )
            return node.getValue()!=null;
        else
            return false;
    }

    @Override
    public void insert(T key, V value) {
        if( key == null || value == null )
            throw new RuntimeErrorException(new Error());
        INode<T, V> node=root;
        //if the tree is empty
        if(node.isNull() ){
            root=new Node<T,V>(key,value,null);
            fix(root);
            return;
        }
        //get node to a node with the given key or its parent
        while (node.getKey().compareTo(key) != 0){
            if(node.getKey().compareTo(key) < 0) {
                if (!node.getRightChild().isNull()){
                    //System.out.println("node's key = " + key + " node's rightChild key = " + node.getRightChild().getKey());
                    node = node.getRightChild();
                }else
                    break;
            }
            else {
                if(!node.getLeftChild().isNull()) {
                    //System.out.println("node's key = " + key + " node's leftChild key = " + node.getLeftChild().getKey());
                    node = node.getLeftChild();
                }else
                    break;
            }
        }
        //if the key is present in the tree
        if(node.getKey().compareTo(key) == 0){
            node.setValue(value);
            return;
        }
        //adding new node with the given key
        INode<T, V> child=new Node<T,V>(key,value,node);
        //setting child as child of node
        if(node.getKey().compareTo(key) < 0)
            node.setRightChild(child);
        else
            node.setLeftChild(child);
        fix(child);
    }
    private void fix(INode<T, V> node){
        //if node equal root
        if(isEqual(node,root))
            node.setColor(INode.BLACK);
        //if node parent is black no violation
        else if(node.getParent().getColor()==INode.BLACK) {
            return;
        }
        //fixing violation ( here its granted that node has a grand parent as node has a parent which is not the root)
        else{
            INode<T, V> parent = node.getParent();
            INode<T, V> uncle = getUncle(node);
            INode<T, V> grandParent = parent.getParent();
            //case 1
            if( uncle.getColor() == INode.RED){
                //recolor node's parent , uncle and grandParent and continue from grandParent
                parent.setColor(INode.BLACK);
                uncle.setColor(INode.BLACK);
                grandParent.setColor(INode.RED);
                fix(grandParent);
            }
            // case 2 and 3
            else {
                //when node's parent is a leftChild
                if(isLeftChild(parent) ){
                    //triangle case parent is a left child and node is a right child
                    if(!isLeftChild(node)){
                        node=parent;
                        leftRotate(node);
                        //reAssign parent after rotation
                        parent=node.getParent();
                    }
                    //line case both parent and node are left children
                    rightRotate(grandParent);
                    parent.setColor(INode.BLACK);
                    grandParent.setColor(INode.RED);
                }
                //when node's parent is a rightChild ( mirror of when parent is left child)
                else {
                    //triangle case parent is a right child and node is a left child
                    if(isLeftChild(node)){
                        node=parent;
                        rightRotate(node);
                        //reAssign parent after rotation
                        parent=node.getParent();
                    }
                    //line case both parent and node are right children
                    leftRotate(grandParent);
                    parent.setColor(INode.BLACK);
                    grandParent.setColor(INode.RED);
                }
            }
        }
    }
    private void leftRotate(INode<T, V> node) {
        INode<T, V> rightChild = node.getRightChild();
        INode<T, V> parent = node.getParent();
        if(parent != null){
            if(isLeftChild(node))
                parent.setLeftChild(rightChild);
            else
                parent.setRightChild(rightChild);
        }else{
            root=rightChild;
        }
        rightChild.getLeftChild().setParent(node);
        node.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(node);
        rightChild.setParent(parent);
        node.setParent(rightChild);
    }
    private void rightRotate(INode<T, V> node){
        INode<T, V> leftChild = node.getLeftChild();
        INode<T, V> parent = node.getParent();
        if(parent != null){
            if(isLeftChild(node))
                parent.setLeftChild(leftChild);
            else
                parent.setRightChild(leftChild);
        }else{
            root=leftChild;
        }
        leftChild.getRightChild().setParent(node);
        node.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(node);
        leftChild.setParent(parent);
        node.setParent(leftChild);
    }
    private INode<T, V> getUncle(INode<T, V> node){
        INode<T, V> parent=node.getParent();
        if( isLeftChild(parent) )
            return parent.getParent().getRightChild();
        else
            return parent.getParent().getLeftChild();
    }
    private boolean isLeftChild(INode<T, V> node){
        return isEqual(node,node.getParent().getLeftChild());
    }
    private boolean isEqual(INode<T, V> node1,INode<T, V> node2){
        if(node1==null && node2==null)
            return true;
        if(node1==null || node2==null)
            return false;
        if(  node1.isNull() && node2.isNull()  )
            return true;
        if(node1.isNull() || node2.isNull())
            return false;
        return  (  node1.getKey().compareTo(node2.getKey())==0  );
    }
    @Override
    public boolean delete(T key) {
        if( key == null )
            throw new RuntimeErrorException(new Error());
        INode<T, V> node=root;
        while ( !node.isNull()  &&  node.getKey().compareTo(key) != 0){
            if(node.getKey().compareTo(key) < 0)
                node=node.getRightChild();
            else
                node=node.getLeftChild();
        }
        boolean result;
        if(node.isNull() || node.getValue() == null)
            result= false;
        else{
            result=true;
            node.setValue(null);
        }
        return result;
    }
}
