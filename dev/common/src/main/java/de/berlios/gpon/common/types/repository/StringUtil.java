/*
GPON General Purpose Object Network
Copyright (C) 2006 Daniel Schulz

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/


package de.berlios.gpon.common.types.repository;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil 
{

  private static Hashtable patternByRegexp = new Hashtable();

  public StringUtil()
  {
  }
  
  public static String rightPad(String in, char padding, int length)
  {
    String out = "";
  
    int toPad = length - in.length();
     
    if (toPad < 0) 
    {
     throw new IllegalArgumentException("Input string longer than requested length");
    }

    out = in + getStringRepetition(padding+"",toPad);  
    
    return out;
  }
  
  public static String leftPad(String in, char padding, int length)
  {
  
    String out = "";
  
    int toPad = length - in.length();
     
    if (toPad < 0) 
    {
     throw new IllegalArgumentException("Input string longer than requested length");
    }

    out = getStringRepetition(padding+"",toPad) + in;  
    
    return out;
  }
  
  public static String getStringRepetition(String in, int times)
  {
    StringBuffer out = new StringBuffer("");
    
    for (int i = 0; i < times; i++) {
      out = out.append(in);      
    }
    
    return out.toString();
  }
  
  public static String removeRegexp(String input, String regexp) {

    if (input==null)
      return null;

    Pattern pattern = null;
    
    if (patternByRegexp.containsKey(regexp)) 
    {
      pattern = (Pattern)patternByRegexp.get(regexp);
    }
    else {
      pattern = Pattern.compile(regexp);
      patternByRegexp.put(regexp,pattern);
    }
    
    Matcher matcher = pattern.matcher(input);
    
    String out = input;
    
    if (matcher.find()) {
      out = out.substring(0,matcher.start())+out.substring(matcher.end());    
    }

    return out;    
  }
  
}
