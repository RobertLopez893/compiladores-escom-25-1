package ejemplo_hoc2;
import java_cup.runtime.*;
import java.io.Reader;
%%  /* inicio de declaraciones JFlex  */
%class AnalizadorLexico
%line      /* Se habilita elcontador de lineas. Variable yyline, de tipo integer  */
%column    /* Se habilita el contado de columnas. Variable yycolumn, de tipo integer */
%char      /* Se habilita el contador de caracteres. Variable yychar, de tipo long  */
%cup    /* Se habilita la compatibilidad con java cup   */

 /* el código entre %{ y %} se copia tal cual dentro de la clase del analizador léxico */
%{
   public SymbolHoc s;
   public MaquinaHoc4 maqHoc;
   public int TipSimb;
 /* Se crean los objetos Symbol para ser utilizados durante la sintésis de los atributos 
    Symbol está especificado en java.cup.Symbol */
  private Symbol symbol(int type){
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value){
    return new Symbol(type,yyline, yycolumn, value);
  }

%}

/* hacemos algunas definiciones regulares, o macro definiciones */
Letra=[a-zA-Z]
Digito=[0-9]
%%  /* Ahora van las expresione regulares */
[ \t\n]+                      { ;}
";"                         {   return symbol(AnalizadorSintacticoSym.SEMIC); }
{Digito}+(\.{Digito}+)?     {   s = new SymbolHoc("",EnumTipoSymbol.CONST_NUM,new Float(yytext()));
                                return  symbol(AnalizadorSintacticoSym.NUM, s);
                            }  
"&&"                        {  return symbol(AnalizadorSintacticoSym.AND); }
"||"                        {  return symbol(AnalizadorSintacticoSym.OR); }
"!"                         {  return symbol(AnalizadorSintacticoSym.NOT); }
">="                        {  return symbol(AnalizadorSintacticoSym.GE); }
">"                         {  return symbol(AnalizadorSintacticoSym.GT); }
"<="                        {  return symbol(AnalizadorSintacticoSym.LE); }
"<"                         {  return symbol(AnalizadorSintacticoSym.LT); }
"=="                        {  return symbol(AnalizadorSintacticoSym.EQ); }
"!="                        { return symbol(AnalizadorSintacticoSym.NE); }
"="                         {  return symbol(AnalizadorSintacticoSym.OpAsig);}
"/"                         {  return symbol(AnalizadorSintacticoSym.OpDiv); } 
"*"                         {  return symbol(AnalizadorSintacticoSym.OpProd); }
"-"                         {  return symbol(AnalizadorSintacticoSym.OpResta); }
"+"                         {  return symbol(AnalizadorSintacticoSym.OpSuma); }
")"                         {  return symbol(AnalizadorSintacticoSym.ParDer); }
"("                         {  return symbol(AnalizadorSintacticoSym.ParIzq); }
"^"                         {  return symbol(AnalizadorSintacticoSym.OpPotencia); }
"{"                         {  return symbol(AnalizadorSintacticoSym.LLAVE_IZQ); }
"}"                         {  return symbol(AnalizadorSintacticoSym.LLAVE_DER); }
"if"                        {  return symbol(AnalizadorSintacticoSym.IF); }
"while"                     {  return symbol(AnalizadorSintacticoSym.WHILE); }  
"else"                      {  return symbol(AnalizadorSintacticoSym.ELSE); }
"print"                     {  return symbol(AnalizadorSintacticoSym.PRINT); }
"switch"                    {  return symbol(AnalizadorSintacticoSym.SWITCH); }
"case"                      {  return symbol(AnalizadorSintacticoSym.CASE); }
"default"                   {  return symbol(AnalizadorSintacticoSym.DEFAULT); }
"break"                     {  return symbol(AnalizadorSintacticoSym.BREAK); }
"for"                       {  return symbol(AnalizadorSintacticoSym.FOR); }
"continue"                  {  return symbol(AnalizadorSintacticoSym.CONTINUE); }
"++"                        { return symbol(AnalizadorSintacticoSym.Aumento); }
"--"                        { return symbol(AnalizadorSintacticoSym.Decremento); }
"+="                        { return symbol(AnalizadorSintacticoSym.AumentoSum); }
"-="                        { return symbol(AnalizadorSintacticoSym.DecrementoRes); }
"*="                        { return symbol(AnalizadorSintacticoSym.AumentoProd); }
"/="                        { return symbol(AnalizadorSintacticoSym.DecrementoDiv); }
":"                         { return symbol(AnalizadorSintacticoSym.DOS_P); }

{Letra}({Letra}|{Digito})*  {   
                                s = maqHoc.TabSimb.lookup(yytext());
                                if(s == null) // Se agregará como variable no inicializada
                                    s= maqHoc.TabSimb.install(yytext(),EnumTipoSymbol.UNDEF,(float)0.0);
                                switch(s.TipoSymbol){
                                    case UNDEF:
                                            TipSimb = AnalizadorSintacticoSym.VAR;
                                            break;
                                    case VAR:
                                            TipSimb = AnalizadorSintacticoSym.VAR;
                                            break;
                                    case BLTIN:
                                            TipSimb = AnalizadorSintacticoSym.BLTIN;
                                            break;
                                    case CONST_PREDEF:
                                            TipSimb = AnalizadorSintacticoSym.CONST_PRED;
                                            break;
                                }
                                return symbol(TipSimb, s);
                            }
. { return symbol(AnalizadorSintacticoSym.error);}