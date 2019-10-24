/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AImmediateExpression extends PExpression
{
    private PImmediate immediate;

    public AImmediateExpression()
    {
        // Constructor
    }

    public AImmediateExpression(
        @SuppressWarnings("hiding") PImmediate _immediate_)
    {
        // Constructor
        setImmediate(_immediate_);

    }

    @Override
    public Object clone()
    {
        return new AImmediateExpression(
            cloneNode(this.immediate));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAImmediateExpression(this);
    }

    public PImmediate getImmediate()
    {
        return this.immediate;
    }

    public void setImmediate(PImmediate node)
    {
        if(this.immediate != null)
        {
            this.immediate.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.immediate = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this.immediate);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.immediate == child)
        {
            this.immediate = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this.immediate == oldChild)
        {
            setImmediate((PImmediate) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
