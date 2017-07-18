package com.pusulait.airsqreen.domain.pg_hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class LongArrayType implements UserType {

	private static final int[] JDBC_TYPES = new int[]{java.sql.Types.ARRAY};

    @Override
    public int[] sqlTypes() {
		return JDBC_TYPES;
	}

	@Override
    public Class<Long[]> returnedClass() {
		return Long[].class;
	}

	@Override
    public boolean equals( Object o1, Object o2 ) throws HibernateException {
		if (o1 == null && o2 == null)
			return true;
		else if (o1 == null || o2 == null)
			return false;
		if(o1 instanceof long[] && o2 instanceof long[]) {
		    return Arrays.equals(((long[]) o1), ((long[])o2));
		} else {
		    return Arrays.equals((Long[]) o1, (Long[]) o2);
		}
	}

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {

		if (strings.length != 1)
			throw new IllegalArgumentException("strings.length != 1, strings = " + strings);

		Array value = resultSet.getArray(strings[0]);

		if (value == null) {
			return null;
		} else {
			return value.getArray();
		}
	}


    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        // use JDBC array type, which is fairly new, so tread carefully
        if (o == null) {
			preparedStatement.setNull(i, java.sql.Types.ARRAY);
		} else if(o instanceof Long[]){
		    Long[] myArray = (Long[]) o;
            Array inArray = preparedStatement.getConnection().createArrayOf("numeric", myArray);
            preparedStatement.setArray(i, inArray);
		}
		else if(o instanceof long[]) {
            long[] myArray = (long[]) o;
            Array inArray = preparedStatement.getConnection().createArrayOf("numeric", wrap(myArray));
			preparedStatement.setArray(i, inArray);
		} else {
		    throw new IllegalArgumentException("Invalid type of input: " + o.getClass().getName());
		}
	}

	@Override
    public Object deepCopy( Object o ) throws HibernateException {
        if (o == null) return null;
        else if(o instanceof Long[])
        {
            Long[] array = (Long[]) o;
            return array.clone();
        }
        else if(o instanceof long[])
        {
            long[] array = (long[]) o;
            return array.clone();
        } else {
            throw new IllegalArgumentException("Invalid type to copy: " + o.getClass().getName());
        }
	}

	@Override
    public boolean isMutable() {
		return true;
	}

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    private static Object[] wrap(long[] longArray) {
        Long[] result = new Long[longArray.length];
        for (int i = 0; i < longArray.length; i++) {
            result[i] = Long.valueOf(longArray[i]);
        }
        return result;
    }
}
