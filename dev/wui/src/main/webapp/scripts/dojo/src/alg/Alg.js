dojo.provide("dojo.alg.Alg");
dojo.require("dojo.lang.array");
dj_deprecated("dojo.alg.Alg is deprecated, use dojo.lang instead");

dojo.alg = dojo.lang;

dojo.alg.for_each = dojo.alg.forEach; // burst compat

dojo.alg.for_each_call = dojo.alg.map; // burst compat

dojo.alg.inArr = dojo.lang.inArray; // burst compat
