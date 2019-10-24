/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class ALabelStatement extends PStatement
{
    private PLabelName labelName;
    private TColon colon;

    public ALabelStatement()
    {
        // Constructor
    }

    public ALabelStatement(
        @SuppressWarnings("hiding") PLabelName _labelName_,
        @SuppressWarnings("hiding") TColon _colon_)
    {
        // Constructor
        setLabelName(_labelName_);

        setColon(_colon_);

    }

    @Override
    public Object clone()
    {
        return new ALabelStatement(
            cloneNode(this.labelName),
            cloneNode(this.colon));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALabelStatement(this);
    }

    public PLabelName getLabelName()
    {
        return this.labelName;
    }

    public void setLabelName(PLabelName node)
    {
        if(this.labelName != null)
        {
            this.labelName.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.labelName = node;
    }

    public TColon getColon()
    {
        return this.colon;
    }

    public void setColon(TColon node)
    {
        if(this.colon != null)
        {
            this.colon.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.colon = node;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("").append(toString(this.labelName)).append(toString(this.colon)).toString();
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.labelName == child)
        {
            this.labelName = null;
            return;
        }

        if(this.colon == child)
        {
            this.colon = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this.labelName == oldChild)
        {
            setLabelName((PLabelName) newChild);
            return;
        }

        if(this.colon == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
