package com.yuanheng100.exception;

public class UnauthorizedException extends PlatformException
{

    /**
     * 
     */
    private static final long serialVersionUID = -4856140518421951699L;

    public UnauthorizedException(String arg0)
    {
        super(arg0);
    }

    public UnauthorizedException(Throwable arg0)
    {
        super(arg0);
    }

    public UnauthorizedException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

}
