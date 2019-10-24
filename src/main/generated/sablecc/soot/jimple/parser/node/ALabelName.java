/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class ALabelName extends PLabelName
{
    private TIdentifier identifier;

    public ALabelName()
    {
        // Constructor
    }

    public ALabelName(
        @SuppressWarnings("hiding") TIdentifier _identifier_)
    {
        // Constructor
        setIdentifier(_identifier_);

    }

    @Override
    public Object clone()
    {
        return new ALabelName(
            cloneNode(this.identifier));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALabelName(this);
    }

    public TIdentifier getIdentifier()
    {
        return this.identifier;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this.identifier != null)
        {
            this.identifier.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.identifier = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this.identifier);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.identifier == child)
        {
            this.identifier = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this.identifier == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
