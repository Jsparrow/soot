/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.node;

import java.util.*;
import soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AMethodMember extends PMember
{
    private final LinkedList<PModifier> modifier = new LinkedList<>();
    private PType type;
    private PName name;
    private TLParen lParen;
    private PParameterList parameterList;
    private TRParen rParen;
    private PThrowsClause throwsClause;
    private PMethodBody methodBody;

    public AMethodMember()
    {
        // Constructor
    }

    public AMethodMember(
        @SuppressWarnings("hiding") List<?> _modifier_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") PName _name_,
        @SuppressWarnings("hiding") TLParen _lParen_,
        @SuppressWarnings("hiding") PParameterList _parameterList_,
        @SuppressWarnings("hiding") TRParen _rParen_,
        @SuppressWarnings("hiding") PThrowsClause _throwsClause_,
        @SuppressWarnings("hiding") PMethodBody _methodBody_)
    {
        // Constructor
        setModifier(_modifier_);

        setType(_type_);

        setName(_name_);

        setLParen(_lParen_);

        setParameterList(_parameterList_);

        setRParen(_rParen_);

        setThrowsClause(_throwsClause_);

        setMethodBody(_methodBody_);

    }

    @Override
    public Object clone()
    {
        return new AMethodMember(
            cloneList(this.modifier),
            cloneNode(this.type),
            cloneNode(this.name),
            cloneNode(this.lParen),
            cloneNode(this.parameterList),
            cloneNode(this.rParen),
            cloneNode(this.throwsClause),
            cloneNode(this.methodBody));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodMember(this);
    }

    public LinkedList<PModifier> getModifier()
    {
        return this.modifier;
    }

    public void setModifier(List<?> list)
    {
        this.modifier.forEach(e -> e.parent(null));
        this.modifier.clear();

        list.stream().map(obj_e -> (PModifier) obj_e).forEach(e -> {
			if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }
			e.parent(this);
			this.modifier.add(e);
		});
    }

    public PType getType()
    {
        return this.type;
    }

    public void setType(PType node)
    {
        if(this.type != null)
        {
            this.type.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.type = node;
    }

    public PName getName()
    {
        return this.name;
    }

    public void setName(PName node)
    {
        if(this.name != null)
        {
            this.name.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.name = node;
    }

    public TLParen getLParen()
    {
        return this.lParen;
    }

    public void setLParen(TLParen node)
    {
        if(this.lParen != null)
        {
            this.lParen.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.lParen = node;
    }

    public PParameterList getParameterList()
    {
        return this.parameterList;
    }

    public void setParameterList(PParameterList node)
    {
        if(this.parameterList != null)
        {
            this.parameterList.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.parameterList = node;
    }

    public TRParen getRParen()
    {
        return this.rParen;
    }

    public void setRParen(TRParen node)
    {
        if(this.rParen != null)
        {
            this.rParen.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.rParen = node;
    }

    public PThrowsClause getThrowsClause()
    {
        return this.throwsClause;
    }

    public void setThrowsClause(PThrowsClause node)
    {
        if(this.throwsClause != null)
        {
            this.throwsClause.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.throwsClause = node;
    }

    public PMethodBody getMethodBody()
    {
        return this.methodBody;
    }

    public void setMethodBody(PMethodBody node)
    {
        if(this.methodBody != null)
        {
            this.methodBody.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this.methodBody = node;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("").append(toString(this.modifier)).append(toString(this.type)).append(toString(this.name)).append(toString(this.lParen))
				.append(toString(this.parameterList)).append(toString(this.rParen)).append(toString(this.throwsClause)).append(toString(this.methodBody)).toString();
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this.modifier.remove(child))
        {
            return;
        }

        if(this.type == child)
        {
            this.type = null;
            return;
        }

        if(this.name == child)
        {
            this.name = null;
            return;
        }

        if(this.lParen == child)
        {
            this.lParen = null;
            return;
        }

        if(this.parameterList == child)
        {
            this.parameterList = null;
            return;
        }

        if(this.rParen == child)
        {
            this.rParen = null;
            return;
        }

        if(this.throwsClause == child)
        {
            this.throwsClause = null;
            return;
        }

        if(this.methodBody == child)
        {
            this.methodBody = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PModifier> i = this.modifier.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PModifier) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this.type == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this.name == oldChild)
        {
            setName((PName) newChild);
            return;
        }

        if(this.lParen == oldChild)
        {
            setLParen((TLParen) newChild);
            return;
        }

        if(this.parameterList == oldChild)
        {
            setParameterList((PParameterList) newChild);
            return;
        }

        if(this.rParen == oldChild)
        {
            setRParen((TRParen) newChild);
            return;
        }

        if(this.throwsClause == oldChild)
        {
            setThrowsClause((PThrowsClause) newChild);
            return;
        }

        if(this.methodBody == oldChild)
        {
            setMethodBody((PMethodBody) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
