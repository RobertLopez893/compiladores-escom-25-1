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
public class InstrucPrograma {
    public EnumTipoInstr TipInstr; /* INSTRUC,SYMBOL,BLTIN, JUMP; */
    public EnumInstrMaq Instruc;  /* EVAL, ADD, SUB, MUL, DIV, NEGATE,POWER, ASSIGN, 
                                     BLTIN, VARPUSH, CONSTPUSH, PRINT, STOP */
    public EnumBLTIN Func_BLTIN; /* SIN, COS, ATAN, LOG, LO10, EXP, SQRT, INTEGER,ABS */
    public SymbolHoc symbolHoc;  /* Apuntador a Nodo Symbol en el doc original de Hoc4 */
    public int jump;             /* posición del arreglo prog a la que se debe saltar  */
    
}
