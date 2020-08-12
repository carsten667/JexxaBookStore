package de.ck.jexxatutorial.bookstore.domain.valueobject;


import java.util.Objects;

public class ISBN13
{
    private String value;

    public ISBN13(final String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ISBN13 isbn13 = (ISBN13) o;
        return Objects.equals(value, isbn13.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value);
    }
}
