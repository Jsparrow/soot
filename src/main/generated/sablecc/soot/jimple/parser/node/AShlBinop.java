/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AShlBinop extends PBinop
{
    private TShl shl;

    public AShlBinop()
    {
        // Constructor
    }

    public AShlBinop(
        @SuppressWarnings("hiding") TShl _shl_)
    {
        // Constructor
        setShl(_shl_);

    }

    @Override
    public Object clone()
    {
        return new AShlBinop(
            cloneNode(this.shl));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShlBinop(this);
    }

    public TShl getShl()
    {
        return this.shl;
    }

    public void setShl(TShl node)
    {
        if(this.shl != null)
        {
            this.shl.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.shl = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this.shl);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.shl == child)
        {
            this.shl = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this.shl == oldChild)
        {
            setShl((TShl) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
