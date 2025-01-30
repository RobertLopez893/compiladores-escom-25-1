/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo_hoc2;

/**
 *
 * @author ocax
 */
public class SymbolHoc {
    public String name;
    public EnumTipoSymbol TipoSymbol; /* VAR, UNDEF, BLTIN, CONST_NUM, CONST_PREDEF*/
    public float val;
    public EnumBLTIN FuncPredef;  /* double (*ptr)(),   en el doc de hoc4 */
    
    public SymbolHoc()
    {
        name="";
        val= 0;
    }
    public SymbolHoc(String nombre, EnumTipoSymbol TipSimbolo, float valor)
    {
        name = nombre;
        TipoSymbol = TipSimbolo;
        val = valor;
    }
    public SymbolHoc(String nombre, EnumTipoSymbol TipSimbolo, EnumBLTIN func)
    {
        name = nombre;
        TipoSymbol = TipSimbolo;
        FuncPredef = func;
    }
    public void SetSymbol(String nombre, EnumTipoSymbol TipSimbolo, float valor)
    {
        name = nombre;
        TipoSymbol = TipSimbolo;
        val = valor;
    }
    public void SetSymbol(String nombre, EnumTipoSymbol TipSimbolo, EnumBLTIN func)
    {
        name = nombre;
        TipoSymbol = TipSimbolo;
        FuncPredef = func;
    }
}
