/**
* <copyright>
*
* Copyright (c) 2005, 2007 IBM Corporation and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM - Initial API and implementation
*   E.D.Willink - Lexer and Parser refactoring to support extensibility and flexible error handling
*
* </copyright>
*
* $Id: QvtOpKWLexerprs.java,v 1.25 2008/11/27 14:24:58 sboyko Exp $
*/
/**
* <copyright>
*
* Copyright (c) 2006-2008 Borland Inc.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Borland - Initial API and implementation
*
* </copyright>
*
* $Id: QvtOpKWLexerprs.java,v 1.25 2008/11/27 14:24:58 sboyko Exp $
*/

package org.eclipse.m2m.internal.qvt.oml.cst.parser;

public class QvtOpKWLexerprs implements lpg.lpgjavaruntime.ParseTable, QvtOpKWLexersym {

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static byte baseCheck[] = {0,
            4,4,2,4,4,5,3,2,3,3,
            7,3,2,4,5,3,3,8,10,10,
            7,6,6,8,3,3,7,6,6,13,
            8,7,11,11,9,8,14,12,12,12,
            6,7,16,4,7,5,6,7,7,10,
            4,10,1,3,5,3,6,14,6,7,
            9,9,6,8,6,6,7,5,6,5,
            4,3,13,10,12,8,5,3,4,3,
            4,3,6,4,7,10,9,12,10,13,
            12,15,9,4,5,7,9,6,7,8,
            8,6,6,4,4,6,4,7,8,9,
            10,13,16,7,6,7,4,4,5,8,
            11,8,8,7,7,2,4,4,6,9,
            4,7,7,9,5,8,10,3,3,7,
            9,7
        };
    };
    public final static byte baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static byte rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            1,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,
            2,2,2,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,146,53,18,76,249,172,
            170,29,136,230,255,85,114,178,257,261,
            265,270,184,122,179,271,186,155,126,101,
            140,81,45,56,142,104,68,49,258,63,
            274,272,276,277,190,282,283,284,285,192,
            288,287,119,197,203,292,208,296,297,298,
            202,301,40,300,207,113,302,299,308,311,
            312,314,107,315,317,316,318,326,213,330,
            319,331,337,334,340,338,344,323,346,349,
            350,353,327,355,356,358,17,144,150,217,
            357,359,151,218,360,366,361,75,368,371,
            369,375,379,382,383,385,388,387,386,389,
            394,396,397,399,400,404,398,412,414,408,
            410,416,415,224,419,420,426,428,429,433,
            437,430,225,228,438,439,446,448,444,452,
            440,169,454,98,455,457,460,463,461,464,
            138,466,468,470,474,475,476,478,477,484,
            25,486,489,492,494,488,496,498,500,501,
            502,504,503,509,512,515,519,521,522,523,
            524,233,236,528,530,533,535,536,539,543,
            245,514,531,540,544,549,547,553,551,556,
            557,558,559,561,564,572,562,575,578,580,
            570,582,583,588,584,586,592,591,597,590,
            598,599,601,603,239,604,610,613,618,611,
            620,616,623,622,627,631,633,634,637,638,
            624,641,639,643,644,651,646,648,653,655,
            657,658,660,666,661,667,671,672,662,675,
            677,678,681,679,685,690,691,693,694,697,
            698,699,706,700,702,709,712,711,715,717,
            720,718,716,726,728,731,727,735,736,737,
            738,742,746,748,750,743,753,755,757,756,
            760,763,767,768,762,771,751,772,773,774,
            782,775,780,784,786,791,788,794,793,795,
            796,802,804,805,69,807,809,811,813,814,
            819,818,822,823,824,826,827,830,834,837,
            839,841,831,844,684,845,842,849,850,854,
            857,858,862,863,865,866,868,869,870,871,
            873,872,876,881,877,888,889,891,895,897,
            893,899,900,901,902,904,906,907,913,916,
            914,908,920,921,922,927,923,930,931,935,
            926,940,938,941,943,946,947,948,954,955,
            957,958,956,960,961,965,972,963,967,973,
            975,977,978,979,980,982,985,986,989,991,
            992,241,997,1000,1004,1005,160,1006,1008,993,
            1012,1009,1017,1018,234,1021,1020,1023,1024,1026,
            1030,1025,1035,1033,1039,1042,1044,1045,1046,1049,
            1048,1053,1056,1057,1060,1064,1066,1067,1070,1071,
            1076,1072,1080,1082,1083,1086,1058,1087,1089,1091,
            1093,1094,1096,1099,1102,1103,1104,1105,1108,1109,
            1110,1111,1115,1116,1119,1120,1117,1128,1124,1131,
            1133,1134,1135,1138,1143,1141,1137,1147,1029,1149,
            1150,1151,1152,1155,1156,1154,1157,1164,1166,1169,
            1172,1175,1176,1177,1178,1181,1183,247,1184,1187,
            1190,1188,1194,1195,1198,1200,1202,1201,1206,1209,
            1213,1214,1208,1218,1217,1222,1223,1225,1226,1228,
            1229,1230,1232,1237,1231,1239,1240,1244,1245,701,
            701
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,0,0,18,19,
            20,21,22,23,0,25,26,11,0,29,
            30,31,16,33,34,35,8,9,38,0,
            40,2,3,19,0,6,7,3,0,1,
            2,27,8,14,10,0,12,9,3,42,
            5,6,0,24,0,10,22,0,0,2,
            3,23,10,6,0,0,2,22,4,4,
            0,14,15,9,0,1,6,19,4,9,
            10,7,18,9,20,15,28,0,30,31,
            0,1,0,0,4,37,0,7,2,9,
            7,8,0,0,1,3,19,5,0,16,
            7,0,9,20,27,0,5,15,7,11,
            9,25,7,8,9,0,39,0,41,0,
            1,0,7,0,3,2,5,4,9,0,
            0,12,0,10,0,1,2,7,21,0,
            11,12,12,14,27,6,16,26,0,0,
            2,0,3,36,6,6,5,0,0,8,
            21,4,14,0,1,0,8,28,10,0,
            7,0,7,4,9,0,0,20,9,8,
            4,0,0,2,2,14,0,0,1,13,
            8,4,0,7,2,9,0,0,1,18,
            4,4,6,0,0,1,3,0,4,0,
            1,8,0,0,22,0,9,5,0,12,
            0,9,7,8,0,1,0,9,0,9,
            12,18,4,0,0,9,0,0,14,19,
            0,1,29,6,0,19,12,3,12,0,
            0,0,3,0,1,0,0,1,7,9,
            5,0,0,0,0,1,0,0,5,2,
            8,0,6,2,13,0,0,0,0,0,
            0,0,5,7,4,6,5,0,13,11,
            0,0,5,0,0,0,0,0,0,9,
            5,8,0,9,8,0,0,2,17,0,
            0,2,6,0,17,5,0,0,2,0,
            1,23,9,0,7,0,24,2,0,0,
            1,8,0,5,0,0,0,0,0,0,
            0,6,5,11,10,0,1,0,0,13,
            0,4,0,13,0,17,8,7,0,5,
            2,0,0,2,0,0,0,0,0,7,
            31,4,6,0,10,0,0,0,0,0,
            15,13,4,0,8,12,7,0,13,0,
            7,0,1,0,0,0,3,0,0,0,
            13,24,7,5,5,0,17,0,0,0,
            5,4,0,1,20,7,0,0,0,0,
            11,0,6,0,7,0,1,0,1,6,
            12,0,13,0,0,1,0,6,5,0,
            0,5,0,0,5,0,4,0,1,0,
            1,6,12,0,0,0,0,0,15,5,
            5,8,5,0,1,0,1,0,0,13,
            2,0,1,0,1,0,1,0,1,0,
            0,0,0,0,0,6,19,4,0,1,
            9,0,1,0,0,15,14,3,0,1,
            0,0,0,0,3,3,6,0,1,0,
            0,8,0,1,0,0,2,2,0,0,
            2,12,0,0,2,32,0,4,0,1,
            0,5,0,1,15,0,0,0,0,2,
            0,0,32,0,0,5,16,4,12,0,
            12,0,1,18,0,1,15,0,1,0,
            1,0,0,0,15,0,5,0,1,0,
            0,0,9,8,3,5,0,0,0,17,
            0,5,0,0,15,8,6,4,10,0,
            0,2,0,3,12,0,4,0,1,0,
            1,0,0,0,9,3,0,1,5,8,
            0,1,0,0,1,3,0,0,0,3,
            0,1,0,0,7,0,8,0,5,7,
            0,1,0,6,0,10,0,0,6,0,
            0,0,8,6,8,0,0,8,3,3,
            0,0,2,13,0,14,0,0,0,8,
            0,4,6,0,0,11,8,7,4,0,
            0,1,0,0,5,2,0,0,0,0,
            8,0,4,20,8,0,1,10,0,10,
            0,0,4,2,0,0,0,0,8,0,
            1,7,5,22,8,0,0,0,3,2,
            0,16,6,3,0,0,0,0,1,4,
            4,0,0,9,3,0,1,0,1,0,
            0,2,0,1,0,0,0,3,2,0,
            18,0,0,8,2,6,0,0,18,3,
            0,0,0,0,0,8,4,6,17,0,
            10,0,1,0,10,0,7,0,3,16,
            0,1,0,0,0,0,1,10,5,7,
            17,0,8,0,0,2,0,1,0,0,
            0,10,0,0,2,7,6,0,0,6,
            2,0,0,0,1,0,0,6,11,0,
            0,2,10,0,30,2,0,1,0,14,
            0,0,4,0,0,15,6,4,0,0,
            2,25,11,0,10,2,0,0,9,2,
            4,0,0,2,0,0,2,0,0,0,
            0,0,0,11,2,0,0,10,13,3,
            0,11,11,8,16,5,17,0,0,1,
            0,1,0,6,0,1,0,1,0,0,
            0,0,2,0,12,0,0,0,7,6,
            12,12,0,0,2,0,11,11,3,0,
            0,0,0,10,5,0,0,20,2,0,
            0,10,3,3,0,1,16,0,16,0,
            0,16,0,4,2,0,0,0,11,4,
            10,4,6,0,0,0,0,0,1,0,
            0,6,0,9,0,1,0,14,12,9,
            11,0,0,2,0,1,0,0,0,0,
            1,0,4,2,0,0,1,21,0,1,
            0,0,0,16,18,33,0,13,26,0,
            1,10,6,0,0,0,16,0,0,1,
            5,0,8,10,7,4,0,0,2,0,
            0,29,0,0,0,0,3,2,0,0,
            13,11,0,9,0,13,7,3,0,1,
            8,0,23,0,0,0,5,0,0,21,
            6,3,0,1,11,0,0,0,11,0,
            1,16,6,0,9,0,0,1,5,0,
            0,0,3,3,17,0,11,2,7,0,
            1,0,0,1,3,0,0,1,0,4,
            0,1,0,0,2,0,1,9,0,1,
            7,0,0,0,0,3,3,0,0,0,
            0,1,11,4,0,0,0,3,0,0,
            1,17,15,0,6,9,18,0,5,2,
            0,1,0,0,0,1,0,0,6,2,
            0,5,0,28,11,3,0,7,0,0,
            0,0,1,0,0,0,0,7,0,10,
            14,7,7,0,8,0,1,4,0,21,
            2,0,19,2,0,0,0,0,4,4,
            0,1,0,0,2,2,0,0,2,0,
            14,14,3,0,0,1,9,0,1,0,
            0,0,0,10,3,0,6,0,0,4,
            11,3,0,0,1,3,0,0,2,12,
            3,0,0,1,0,0,1,0,0,0,
            0,0,11,9,3,8,0,7,0,0,
            11,3,3,0,0,1,0,0,5,21,
            0,0,0,0,0,19,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            701,174,175,170,176,169,177,173,162,172,
            163,155,150,151,168,156,701,701,152,161,
            153,164,149,157,701,154,171,708,701,167,
            158,160,726,166,165,148,184,185,147,701,
            159,285,284,398,701,283,281,230,701,251,
            249,397,231,282,229,701,228,250,236,700,
            235,234,701,286,701,233,227,701,701,245,
            247,248,253,244,13,701,333,232,336,178,
            701,704,246,334,701,189,224,537,191,226,
            223,190,332,827,335,225,539,701,540,538,
            701,219,701,701,218,541,701,217,717,216,
            241,242,701,701,194,291,379,292,701,240,
            193,701,192,243,382,701,204,290,203,267,
            205,298,215,213,214,701,380,701,381,701,
            220,701,186,701,238,317,239,318,222,701,
            701,221,701,316,701,212,211,325,388,27,
            321,319,326,320,390,612,840,237,701,701,
            375,701,182,389,376,183,181,701,701,180,
            613,195,377,701,202,701,207,614,206,701,
            201,701,210,258,209,701,701,196,259,263,
            268,701,701,279,271,264,701,701,273,269,
            270,274,701,288,303,289,701,701,328,278,
            322,327,323,701,701,365,772,701,366,701,
            187,357,701,701,304,701,368,414,701,367,
            85,413,415,416,701,423,89,450,701,609,
            451,621,179,701,701,680,701,701,702,608,
            701,198,620,252,701,679,188,199,197,701,
            701,701,200,701,254,701,701,257,255,208,
            256,701,701,701,701,262,701,701,261,265,
            773,701,266,272,260,701,701,701,701,701,
            701,701,277,276,280,287,293,701,275,294,
            701,701,295,701,701,701,701,701,701,296,
            301,297,701,299,300,701,701,302,718,701,
            701,713,312,701,783,305,701,701,711,701,
            307,781,306,701,308,701,309,757,701,701,
            727,710,8,310,701,701,701,701,701,701,
            701,314,315,311,313,701,330,701,701,324,
            701,337,701,331,701,839,339,338,701,340,
            819,701,701,818,701,701,701,701,701,341,
            329,343,795,701,342,701,701,701,701,701,
            344,345,349,701,348,346,350,78,347,701,
            351,701,353,701,701,701,805,701,701,701,
            354,352,356,358,359,701,355,701,701,701,
            745,360,701,362,785,361,701,701,701,701,
            364,701,363,701,369,701,371,701,808,372,
            370,701,374,701,701,383,701,373,378,701,
            701,384,701,701,385,701,386,701,782,701,
            392,391,387,701,701,701,701,701,832,752,
            394,393,395,701,396,701,399,701,701,780,
            400,701,401,701,402,701,404,701,405,701,
            701,701,701,54,701,406,403,408,701,706,
            407,701,409,701,701,829,828,410,701,715,
            701,701,701,701,705,411,806,701,417,701,
            701,412,701,419,701,701,703,420,701,701,
            421,418,701,701,422,820,701,426,701,427,
            701,428,701,429,425,701,701,701,701,432,
            701,701,424,701,701,434,769,435,431,701,
            433,701,436,430,701,796,437,701,756,701,
            747,701,701,701,438,701,439,701,836,701,
            701,701,441,442,443,444,701,701,701,440,
            701,445,701,701,446,447,778,452,448,701,
            701,453,701,456,449,701,454,701,455,701,
            457,701,701,701,458,459,701,461,467,460,
            701,462,701,701,464,463,701,701,701,465,
            701,716,701,701,466,701,468,701,470,469,
            701,471,701,473,701,472,701,701,474,701,
            701,701,475,477,476,701,701,478,479,481,
            701,701,482,480,701,707,701,701,701,483,
            701,486,485,701,701,484,488,487,489,701,
            701,491,701,701,490,771,701,701,701,701,
            492,701,495,807,493,701,496,494,701,497,
            701,701,499,500,701,701,701,701,501,701,
            503,502,504,498,770,701,701,701,506,507,
            701,505,764,508,701,701,701,701,512,510,
            511,701,701,509,513,701,514,701,515,701,
            701,804,701,767,701,701,701,766,730,701,
            516,701,701,517,519,518,701,701,524,520,
            701,701,701,701,701,521,526,525,522,701,
            523,701,527,701,528,701,529,701,530,748,
            701,816,701,701,701,701,534,531,723,532,
            742,701,533,701,701,758,701,542,701,701,
            701,535,701,701,784,543,799,701,701,544,
            545,701,701,701,548,701,701,724,546,701,
            701,551,547,701,536,760,701,552,701,549,
            701,701,553,701,701,555,554,556,701,701,
            558,550,557,701,803,560,701,701,559,562,
            561,701,701,563,701,701,825,701,701,701,
            701,701,701,826,568,701,701,565,564,570,
            701,567,750,743,566,569,768,701,701,571,
            701,733,701,797,701,572,701,817,701,701,
            701,701,576,701,573,701,701,701,577,578,
            574,575,701,701,580,701,749,579,746,701,
            701,701,701,581,833,701,701,815,809,701,
            701,582,584,585,701,586,761,701,583,701,
            701,587,701,588,590,129,701,701,834,591,
            589,592,800,701,701,701,701,701,722,701,
            701,712,701,593,701,596,28,841,594,595,
            843,701,701,597,701,600,701,701,701,701,
            824,701,602,603,701,701,605,599,701,606,
            701,701,701,777,601,598,701,604,801,701,
            823,607,821,701,701,701,837,701,701,719,
            615,701,611,610,616,618,701,701,810,701,
            701,617,701,701,701,701,624,802,701,701,
            619,622,701,626,701,623,625,627,701,725,
            628,701,737,701,701,701,629,701,701,663,
            765,631,701,835,630,701,701,701,842,701,
            794,732,763,701,632,701,701,634,762,701,
            701,701,788,635,642,701,633,636,637,701,
            638,701,701,640,639,701,701,643,701,641,
            701,736,701,701,645,701,647,644,701,648,
            646,701,701,701,701,650,831,701,701,701,
            701,811,649,653,701,701,701,775,701,701,
            655,798,651,701,838,656,652,701,658,657,
            701,812,701,701,701,751,701,701,659,721,
            701,661,701,654,753,720,701,660,701,701,
            701,701,667,86,701,701,701,666,701,665,
            662,668,669,701,822,701,672,671,701,664,
            673,701,670,674,701,701,701,701,675,676,
            701,677,701,701,678,681,701,701,682,701,
            735,734,789,701,701,685,683,701,740,701,
            701,701,701,684,686,701,741,701,701,687,
            739,792,701,701,776,688,701,701,690,689,
            774,701,701,691,701,701,693,701,112,701,
            701,701,731,692,759,694,90,697,701,701,
            738,698,793,701,701,814,701,701,744,695,
            701,701,701,701,701,696
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }
    public final int asb(int index) { return 0; }
    public final int asr(int index) { return 0; }
    public final int nasb(int index) { return 0; }
    public final int nasr(int index) { return 0; }
    public final int terminalIndex(int index) { return 0; }
    public final int nonterminalIndex(int index) { return 0; }
    public final int scopePrefix(int index) { return 0;}
    public final int scopeSuffix(int index) { return 0;}
    public final int scopeLhs(int index) { return 0;}
    public final int scopeLa(int index) { return 0;}
    public final int scopeStateSet(int index) { return 0;}
    public final int scopeRhs(int index) { return 0;}
    public final int scopeState(int index) { return 0;}
    public final int inSymb(int index) { return 0;}
    public final String name(int index) { return null; }
    public final int getErrorSymbol() { return 0; }
    public final int getScopeUbound() { return 0; }
    public final int getScopeSize() { return 0; }
    public final int getMaxNameLength() { return 0; }

    public final static int
           NUM_STATES        = 554,
           NT_OFFSET         = 54,
           LA_STATE_OFFSET   = 843,
           MAX_LA            = 1,
           NUM_RULES         = 142,
           NUM_NONTERMINALS  = 3,
           NUM_SYMBOLS       = 57,
           SEGMENT_SIZE      = 8192,
           START_STATE       = 143,
           IDENTIFIER_SYMBOL = 0,
           EOFT_SYMBOL       = 42,
           EOLT_SYMBOL       = 55,
           ACCEPT_ACTION     = 700,
           ERROR_ACTION      = 701;

    public final static boolean BACKTRACK = false;

    public final int getNumStates() { return NUM_STATES; }
    public final int getNtOffset() { return NT_OFFSET; }
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }
    public final int getMaxLa() { return MAX_LA; }
    public final int getNumRules() { return NUM_RULES; }
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }
    public final int getNumSymbols() { return NUM_SYMBOLS; }
    public final int getSegmentSize() { return SEGMENT_SIZE; }
    public final int getStartState() { return START_STATE; }
    public final int getStartSymbol() { return lhs[0]; }
    public final int getIdentifierSymbol() { return IDENTIFIER_SYMBOL; }
    public final int getEoftSymbol() { return EOFT_SYMBOL; }
    public final int getEoltSymbol() { return EOLT_SYMBOL; }
    public final int getAcceptAction() { return ACCEPT_ACTION; }
    public final int getErrorAction() { return ERROR_ACTION; }
    public final boolean isValidForParser() { return isValidForParser; }
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int originalState(int state) { return 0; }
    public final int asi(int state) { return 0; }
    public final int nasi(int state) { return 0; }
    public final int inSymbol(int state) { return 0; }

    public final int ntAction(int state, int sym) {
        return baseAction[state + sym];
    }

    public final int tAction(int state, int sym) {
        int i = baseAction[state],
            k = i + sym;
        return termAction[termCheck[k] == sym ? k : i];
    }
    public final int lookAhead(int la_state, int sym) {
        int k = la_state + sym;
        return termAction[termCheck[k] == sym ? k : la_state];
    }
}
