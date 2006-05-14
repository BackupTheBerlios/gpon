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


package de.berlios.gpon.common.util.search;

import de.berlios.gpon.common.constants.SearchOp;

public class ComparisonOperators {
	  private static ComparisonOperator[] operators;

	  public static ComparisonOperator EQUALS       = new ComparisonOperator(SearchOp.OP_EQUALS,"gpon.op.eq");
	  public static ComparisonOperator NOT_EQUALS   = new ComparisonOperator(SearchOp.OP_NOT_EQUALS,"gpon.op.ne");
	  public static ComparisonOperator GREATER_THAN = new ComparisonOperator(SearchOp.OP_GREATER_THAN,"gpon.op.gt");
	  public static ComparisonOperator LESS_THAN    = new ComparisonOperator(SearchOp.OP_LESS_THAN,"gpon.op.lt");
	  public static ComparisonOperator STARTS       = new ComparisonOperator(SearchOp.OP_STARTS_WITH,"gpon.op.starts");
	  public static ComparisonOperator ENDS         = new ComparisonOperator(SearchOp.OP_ENDS_WITH,"gpon.op.ends");
	  public static ComparisonOperator INCLUDES     = new ComparisonOperator(SearchOp.OP_INCLUDES,"gpon.op.incl");

	  static {
	    operators = new ComparisonOperator[7];
	    operators[0] = EQUALS;
	    operators[1] = NOT_EQUALS;
	    operators[2] = GREATER_THAN;
	    operators[3] = LESS_THAN;
	    operators[4] = STARTS;
	    operators[5] = ENDS;
	    operators[6] = INCLUDES;
	  }

	  public ComparisonOperators()
	  {
	  }


	  public ComparisonOperator[] getOperators()
	  {
	    return operators;
	  }
	 

}
