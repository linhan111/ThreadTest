package com.github.linhan111.T201911;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * parent-children，数据库中递归查询/内存中递归组装还是创建树的形式保存，参考链接：https://www.v2ex.com/t/623139#reply18
 */
public class TreeBuilderTest {
    public static void main(String[] args) {
//        buildTree();


    }


    private static <E, ID> Node<E> buildTree(Collection<E> elements, Function<E, ID> getId, Function<E, ID> getParentId) {
        Map<ID, Node<E>> idNodeMap = elements.stream().collect(Collectors.toMap(getId, Node::new));
        Node<E> root = null;
        for (E element : elements) {
            ID parentId = getParentId.apply(element);
            ID selfId = getId.apply(element);
            Node<E> selfNode = idNodeMap.get(selfId);
            if (idNodeMap.containsKey(parentId)) {
                Node<E> parentNode = idNodeMap.get(parentId);

                parentNode.children.add(selfNode);
                selfNode.parent = parentNode;
            } else {
                if (root != null) {
                    throw new IllegalArgumentException("root node more than one");
                }
                root = selfNode;
            }
        }
        Objects.requireNonNull(root, "there is not root node");
        return root;
    }

    private static class Node<E> {
        E data;
        Set<Node<E>> children;
        Node<E> parent;

        Node(E data) {
            this.data = data;
            children = new HashSet<>();
        }
    }
}
