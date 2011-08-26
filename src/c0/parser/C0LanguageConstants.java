/* Generated By:JavaCC: Do not edit this line. C0LanguageConstants.java */
package c0.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface C0LanguageConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int WHITESPACE = 1;
  /** RegularExpression Id. */
  int INT = 2;
  /** RegularExpression Id. */
  int VOID = 3;
  /** RegularExpression Id. */
  int IF = 4;
  /** RegularExpression Id. */
  int ELSE = 5;
  /** RegularExpression Id. */
  int WHILE = 6;
  /** RegularExpression Id. */
  int FOR = 7;
  /** RegularExpression Id. */
  int RETURN = 8;
  /** RegularExpression Id. */
  int BREAK = 9;
  /** RegularExpression Id. */
  int IDENTIFIER = 10;
  /** RegularExpression Id. */
  int INTEGER = 11;
  /** RegularExpression Id. */
  int LINE_COMMENT = 12;
  /** RegularExpression Id. */
  int STRING = 16;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_STRING = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<WHITESPACE>",
    "\"int\"",
    "\"void\"",
    "\"if\"",
    "\"else\"",
    "\"while\"",
    "\"for\"",
    "\"return\"",
    "\"break\"",
    "<IDENTIFIER>",
    "<INTEGER>",
    "<LINE_COMMENT>",
    "\"\\\"\"",
    "<token of kind 14>",
    "<token of kind 15>",
    "\"\\\"\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "\"=\"",
    "\";\"",
    "\"[\"",
    "\"]\"",
    "\"{\"",
    "\"}\"",
    "\"||\"",
    "\"&&\"",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"++\"",
    "\"--\"",
    "\"!\"",
  };

}