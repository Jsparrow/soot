/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AStringConstant extends PConstant
{
    private TStringConstant stringConstant;

    public AStringConstant()
    {
        // Constructor
    }

    public AStringConstant(
        @SuppressWarnings("hiding") TStringConstant _stringConstant_)
    {
        // Constructor
        setStringConstant(_stringConstant_);

    }

    @Override
    public Object clone()
    {
        return new AStringConstant(
            cloneNode(this.stringConstant));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStringConstant(this);
    }

    public TStringConstant getStringConstant()
    {
        return this.stringConstant;
    }

    public void setStringConstant(TStringConstant node)
    {
        if(this.stringConstant != null)
        {
            this.stringConstant.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.stringConstant = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this.stringConstant);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.stringConstant == child)
        {
            this.stringConstant = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this.stringConstant == oldChild)
        {
            setStringConstant((TStringConstant) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
