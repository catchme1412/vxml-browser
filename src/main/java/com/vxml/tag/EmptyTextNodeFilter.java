package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

public class EmptyTextNodeFilter implements NodeFilter {

    @Override
    public short acceptNode(Node n) {
        if ("text".equals(n.getNodeName()) && n.getNodeValue().trim().isEmpty()|| n.getNodeType() == Node.COMMENT_NODE)  {
            return NodeFilter.FILTER_SKIP;
        }
        return NodeFilter.FILTER_ACCEPT;
    }

}
