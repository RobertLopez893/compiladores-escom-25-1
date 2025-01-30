/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo_hoc2;

import java.util.*;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ocax
 */
public class MaquinaHoc4 {

    TablaSimbolos TabSimb;
    InstrucPrograma Prog[];
    int progp = 0;
    int pc;
    float s = 0;
    Stack<Datum> stack;
    JTextArea AreaResult;
    /*JTable jTablePila;*/

    public MaquinaHoc4() {
        TabSimb = new TablaSimbolos();
        TabSimb.init(); // Se inicializa la tabla de símbolos //

        Prog = new InstrucPrograma[2048];
        progp = 0;
        pc = 0;

        stack = new Stack();
        stack.clear();

    }

    public void initcode() {
        progp = 0;
        stack.clear();
    }

    public Integer code(InstrucPrograma inst) {
        Integer oprogp = progp;
        Prog[progp++] = inst;
        return oprogp;
    }

    public Integer code2(InstrucPrograma inst1, InstrucPrograma inst2) {
        Integer oprogp = progp;
        Prog[progp++] = inst1;
        Prog[progp++] = inst2;
        return oprogp;
    }

    public Integer code3(InstrucPrograma inst1, InstrucPrograma inst2, InstrucPrograma inst3) {
        Integer oprogp = progp;
        Prog[progp++] = inst1;
        Prog[progp++] = inst2;
        Prog[progp++] = inst3;
        return oprogp;
    }

    public Integer code4(InstrucPrograma inst1, InstrucPrograma inst2, InstrucPrograma inst3, InstrucPrograma inst4) {
        Integer oprogp = progp;
        Prog[progp++] = inst1;
        Prog[progp++] = inst2;
        Prog[progp++] = inst3;
        Prog[progp++] = inst4;
        return oprogp;
    }

    public Integer code6(InstrucPrograma inst1, InstrucPrograma inst2, InstrucPrograma inst3, InstrucPrograma inst4, InstrucPrograma inst5, InstrucPrograma inst6) {
        Integer oprogp = progp;
        Prog[progp++] = inst1;
        Prog[progp++] = inst2;
        Prog[progp++] = inst3;
        Prog[progp++] = inst4;
        Prog[progp++] = inst5;
        Prog[progp++] = inst6;
        return oprogp;
    }

    public void execute(int ind) {
        InstrucPrograma instruc;
        Datum op1, op2;
        String CadResult = new String();

        Object os[] = new Object[5];
        String TipDatum = new String();
        String Val = new String();
        String NombSymbol = new String();
        String TypeSymbol = new String();
        String ValSymbol = new String();

//        DefaultTableModel modeloTablaPila = (DefaultTableModel) jTablePila.getModel();

        pc = ind;
        while (Prog[pc].Instruc != EnumInstrMaq.STOP) {

            /*    AreaResult.append("PC = " + Integer.toString(pc)+ "\n"); */
            TipDatum = "";
            Val = "";
            NombSymbol = "";
            TypeSymbol = "";
            ValSymbol = "";

            //modeloTablaPila.fireTableDataChanged();
            instruc = Prog[pc++];
            System.out.println("Instruc.instruc actual:" + instruc.Instruc);
            switch (instruc.Instruc) { //Lo que se tiene en instruc es una instrucción de programa 
                case ADD: // se sacan dos Datum de la pila,se suman sus valores, y el resultado se mete a a pila 
                    add();
                    break;
                case ASSIGN:  // En el tope de la pila se encuentra la inf de la variable a la que se le va a asignar
                    // el valor de una expresión
                    assign();
                    break;
                case BLTIN:
                    bltin();
                    break;
                case CONSTPUSH:
                    constpush();
                    break;
                case DIV:
                    div();
                    break;
                case EVAL:
                    eval();
                    break;
                case MUL:
                    mul();
                    break;
                case NEGATE:
                    negate();
                    break;
                case GT:
                    gt();
                    break;
                case GE:
                    ge();
                    break;
                case LT:
                    lt();
                    break;
                case LE:
                    le();
                    break;
                case EQ:
                    eq();
                    break;
                case NE:
                    ne();
                    break;
                case AND:
                    and();
                    break;
                case OR:
                    or();
                    break;
                case NOT:
                    not();
                    break;
                case POWER:
                    power();
                    break;
                case PRINT:
                    print();
                    break;
                case STOP:
                    return;
                case SUB:
                    sub();
                    break;
                case VARPUSH:
                    varpush();
                    break;
                case WHILECODE:
                    whilecode();
                    break;
                case IFCODE:
                    ifcode();
                    break;
                case SWITCHCODE:
                    switchcode();
                    break;
                case FORCODE:
                    forcode();
                    break;
                case AUMENTO:
                    incrementcode();
                    break;
                case DECREMENTO:
                    decrementcode();
                    break;
                case AUMENTOSUM:
                    add();
                    break;
                case AUMENTOPROD:
                    mul();
                    break;
                case DECREMENTORES:
                    sub();
                    break;
                case DECREMENTODIV:
                    div();
                    break;
                case CASE:
                    casecode();
                    break;
                case DEFAULT:
                    defaultcode();
                    break;     
                case CONTINUE:
                    continuecode();
                    break;
            }
        }
    }

    public void add() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        op1.val += op2.val;
        System.out.println("Add: " + op1.val);
        stack.push(op1);
    }

    public void assign() {
        Datum op1, op2;
        System.out.println("op2=" + stack.peek().val);
        op2 = stack.pop();
        System.out.println("op1=" + stack.peek().val);
        op1 = stack.pop();
        op2.symb.val = op1.val;
        op2.symb.TipoSymbol = EnumTipoSymbol.VAR;
        System.out.println("Assign: " + op2.symb.name + " = " + op1.val);
        stack.push(op1);
    }

    public void bltin() {
        Datum op1, op2;
        InstrucPrograma instruc;
        instruc = Prog[pc++];
        switch (instruc.Func_BLTIN) {
            case ABS:
                op1 = stack.pop();
                op1.val = Math.abs(op1.val);
                System.out.println("ABS: " + op1.val);
                stack.push(op1);
                break;
            case ATAN:
                op1 = stack.pop();
                op1.val = (float) Math.atan((double) op1.val);
                System.out.println("ATAN: " + op1.val);
                stack.push(op1);
                break;
            case COS:
                op1 = stack.pop();
                op1.val = (float) Math.cos((double) op1.val);
                System.out.println("COS: " + op1.val);
                stack.push(op1);
                break;
            case EXP:
                op1 = stack.pop();
                op1.val = (float) Math.exp((double) op1.val);
                System.out.println("EXP: " + op1.val);
                stack.push(op1);
                break;
            case INTEGER:
                op1 = stack.pop();
                op1.val = (float) Math.floor((double) op1.val);
                System.out.println("INTEGER: " + op1.val);
                stack.push(op1);
                break;
            case LOG10:
                op1 = stack.pop();
                op1.val = (float) Math.log10((double) op1.val);
                System.out.println("LOG10: " + op1.val);
                stack.push(op1);
                break;
            case LOG:
                op1 = stack.pop();
                op1.val = (float) Math.log((double) op1.val);
                System.out.println("LOG: " + op1.val);
                stack.push(op1);
                break;
            case SIN:
                op1 = stack.pop();
                op1.val = (float) Math.sin((double) op1.val);
                System.out.println("SIN: " + op1.val);
                stack.push(op1);
                break;
            case SQRT:
                op1 = stack.pop();
                op1.val = (float) Math.sqrt((double) op1.val);
                System.out.println("SQRT: " + op1.val);
                stack.push(op1);
                break;
        }
    }

    public void constpush() {
        Datum op1;
        op1 = new Datum();
        op1.val = Prog[pc++].symbolHoc.val;
        System.out.println("ConstPush: " + op1.val);
        stack.push(op1);
    }

    public void div() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        op1.val /= op2.val;
        System.out.println("Div: " + op1.val);
        stack.push(op1);
    }

    public void eval() {
        Datum op1, op2;
        op2 = new Datum();
        op1 = stack.pop();
        op2.val = op1.symb.val;
        System.out.println("Eval: " + op2.val);
        stack.push(op2);
    }

    public void mul() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        op1.val *= op2.val;
        System.out.println("Mul: " + op1.val);
        stack.push(op1);
    }

    public void negate() {
        Datum op1;
        op1 = stack.pop();
        op1.val = -op1.val;
        System.out.println("Negate: " + op1.val);
        stack.push(op1);
    }

    public void power() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        op1.val = (float) Math.pow((double) op1.val, (double) op2.val);
        System.out.println("Power: " + op1.val);
        stack.push(op1);
    }

    public void print() {
        Datum op1;
        String CadResult = new String();

        System.out.println("[INFO] Inicio de print. Pop del stack.");
        op1 = stack.pop();
        System.out.println("[INFO] Valor extraído del stack (op1.val): " + op1.val);

        CadResult = Float.toString(op1.val) + "\n";
        System.out.println("[INFO] Cadena resultante para agregar a AreaResult: " + CadResult);
        AreaResult.append(CadResult);

        /*
    modeloTablaPila.removeRow(modeloTablaPila.getRowCount() - 1);
         */
        System.out.println("[INFO] Fin de print.");
    }

    public void sub() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        op1.val -= op2.val;
        System.out.println("Sub: " + op1.val);
        stack.push(op1);
    }

    public void varpush() {
        Datum op1;
        op1 = new Datum();
        op1.symb = Prog[pc++].symbolHoc;
        System.out.println("VarPush: " + op1.symb.name + " = " + op1.symb.val);
        stack.push(op1);
    }

    public void gt() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] gt: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val > op2.val) ? 1 : 0;
        System.out.println("[INFO] gt result: " + op1.val);
        stack.push(op1);
    }

    public void ge() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] ge: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val >= op2.val) ? 1 : 0;
        System.out.println("[INFO] ge result: " + op1.val);
        stack.push(op1);
    }

    public void lt() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] lt: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val < op2.val) ? 1 : 0;
        System.out.println("[INFO] lt result: " + op1.val);
        stack.push(op1);
    }

    public void le() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] le: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val <= op2.val) ? 1 : 0;
        System.out.println("[INFO] le result: " + op1.val);
        stack.push(op1);
    }

    public void eq() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] eq: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val == op2.val) ? 1 : 0;
        System.out.println("[INFO] eq result: " + op1.val);
        stack.push(op1);
    }

    public void ne() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] ne: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val != op2.val) ? 1 : 0;
        System.out.println("[INFO] ne result: " + op1.val);
        stack.push(op1);
    }

    public void and() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] and: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = op1.val * op2.val;
        System.out.println("[INFO] and result: " + op1.val);
        stack.push(op1);
    }

    public void or() {
        Datum op1, op2;
        op2 = stack.pop();
        op1 = stack.pop();
        System.out.println("[INFO] or: op1.val=" + op1.val + ", op2.val=" + op2.val);
        op1.val = (op1.val == 1 || op2.val == 1) ? 1 : 0;
        System.out.println("[INFO] or result: " + op1.val);
        stack.push(op1);
    }

    public void not() {
        Datum op1;
        op1 = stack.pop();
        System.out.println("[INFO] not: op1.val=" + op1.val);
        op1.val = (op1.val == 1) ? 0 : 1;
        System.out.println("[INFO] not result: " + op1.val);
        stack.push(op1);
    }

    public void whilecode() {
        int InicioCuerpoWhile, InstrDespuesWhile;
        int AuxPc;
        Datum op1;

        AuxPc = pc;
        System.out.println("[INFO] Inicio de whilecode. pc inicial: " + AuxPc);

        execute(AuxPc + 2); // Ejecutando la condición
        op1 = stack.pop();
        System.out.println("[INFO] Condición inicial: " + op1.val);
        System.out.println(stack.pop());
        while (op1.val != 0) {
            InicioCuerpoWhile = Prog[AuxPc].jump;
            System.out.println("[INFO] Inicio del cuerpo del while en: " + InicioCuerpoWhile);
            execute(InicioCuerpoWhile);

            execute(AuxPc + 2); // Ejecutando la condición nuevamente
            op1 = stack.pop();
            System.out.println("[INFO] Nueva evaluación de la condición: " + op1.val);
        }

        InstrDespuesWhile = Prog[AuxPc + 1].jump;
        System.out.println("[INFO] Fin del while. Saltando a: " + InstrDespuesWhile);
        pc = InstrDespuesWhile;
    }

    public void ifcode() {
        int IniThen, IniElse, FinIF;
        int AuxPc;
        Datum op1;

        AuxPc = pc;
        System.out.println("[INFO] Inicio de ifcode. pc inicial: " + AuxPc);

        execute(AuxPc + 3); // Inicio del código de la condición
        System.out.println("[INFO] Ejecutada condición en pc: " + (AuxPc + 3));

        op1 = stack.pop();
        System.out.println("[INFO] Valor de la condición (op1.val): " + op1.val);

        if (op1.val == 1) {
            IniThen = Prog[AuxPc].jump;
            System.out.println("[INFO] Condición verdadera. Saltando a IniThen: " + IniThen);
            execute(IniThen);
        } else {
            if (Prog[AuxPc + 1].TipInstr == EnumTipoInstr.JUMP) {
                IniElse = Prog[AuxPc + 1].jump;
                System.out.println("[INFO] Condición falsa. Saltando a IniElse: " + IniElse);
                execute(IniElse);
            } else {
                System.out.println("[INFO] Condición falsa. No se ejecuta bloque Else (no es JUMP).");
            }
        }

        FinIF = Prog[AuxPc + 2].jump;
        System.out.println("[INFO] Fin del if. Saltando a FinIF: " + FinIF);
        pc = FinIF;
        System.out.println("[INFO] ifcode completado. pc final: " + pc);
    }

    public void forcode() {
        int InicioCuerpoFor, InstrDespuesFor;
        int AuxPc;
        Datum op1;

        AuxPc = pc;
        System.out.println("[INFO] Inicio de forcode. pc inicial: " + AuxPc);

        // Ejecutando la inicialización del for
        System.out.println("Sigueinte:" + (AuxPc + 5));
        System.out.println("AuxPc: " + AuxPc);
        execute(AuxPc + 5);
        System.out.println("[INFO] Inicialización del for ejecutada: " + AuxPc + 5);

        op1 = stack.pop();
        System.out.println("[INFO] Condición inicial: " + op1.val);

        int aux = AuxPc;

        while (op1.val != 0) {
            InicioCuerpoFor = Prog[AuxPc + 3].jump;
            System.out.println("[INFO] Inicio del cuerpo del for en: " + InicioCuerpoFor);
            execute(InicioCuerpoFor);

            System.out.println("Iteración ejecutada.");

            // Ejecutando la actualización del for
            System.out.println("Aux después de la ejecución:" + AuxPc);
            execute(Prog[AuxPc + 2].jump);
            System.out.println("[INFO] Actualización del for ejecutada.");

            // Evaluando la condición nuevamente
            System.out.println("Aux después de la asignación:" + AuxPc);
            execute(Prog[AuxPc + 1].jump);
            op1 = stack.pop();
            System.out.println("[INFO] Nueva evaluación de la condición: " + op1.val);
        }

        InstrDespuesFor = Prog[AuxPc + 4].jump;
        System.out.println("[INFO] Fin del for. Saltando a: " + InstrDespuesFor);
        pc = InstrDespuesFor;
    }

    public void switchcode() {
        int AuxPc;

        // AuxPc = pc => donde se generó SWITCHCODE
        AuxPc = pc;
        System.out.println("[INFO] Inicio switchcode, pc inicial: " + AuxPc);
        
        // 1) Si tu gramática NO ha evaluado la expresión del switch,
        //    podrías hacer algo como:
        //
        // Mueve pc a la siguiente instrucción tras SWITCHCODE.
        // (Es frecuente que se mueva 1, pero depende de cuántas instr. generaste.)
       //System.out.println(stack.pop().val);
        
        execute(Prog[AuxPc].jump);
    }

    public void casecode() {
        System.out.println("[CASECODE] pc actual: " + pc);

        // 1) Extraemos el valor del 'case' que la gramática dejó en la pila
        //    (por ejemplo, con CONSTPUSH antes de CASE).
        Datum caseVal = new Datum();
        caseVal.val = s;
        System.out.println("[CASECODE] Valor del case: " + caseVal.val);

        // 2) Extraemos el valor del switch
        //    (si tu diseño es que el switchValue también está en la pila).
        //    Si en cambio lo tienes guardado en un atributo, úsalo directamente.
        Datum switchVal = stack.peek();
        System.out.println("[CASECODE] Valor del switch: " + switchVal.val);

        // 3) Comparamos
        if (switchVal.val == caseVal.val) {
            // 4) Si coincide, "hacemos el jump del pc actual"
            int jumpTo = Prog[pc++].jump;  // Suponiendo que en Prog[pc] tenemos el JUMP
            System.out.println("[CASECODE] => ¡Coincide! Saltando a " + jumpTo);
            pc = jumpTo;

            // Dependiendo de tu diseño, podrías:
            // - Dejar que el while principal (en execute()) continúe,
            //   ya con pc = jumpTo
            // - Llamar a execute(pc) de forma recursiva
            //   (aunque corres riesgo de bucles si no está controlado)
            // Ejemplo:
            // execute(pc);
        } else {
            // 5) Si NO coincide, pc++ para tomar el siguiente jump
            System.out.println("[CASECODE] => No coincide, incrementamos pc para ir a next case");
            pc++;
            s++;

            int jumpNext = Prog[pc++].jump;
            System.out.println("[CASECODE] => Jump al siguiente case (u otra sección): " + jumpNext);

            // Ajustas pc = jumpNext o llamas a execute(jumpNext), según la lógica:
            pc = jumpNext;
            // O:
            // execute(jumpNext);
        }
        
    }

    public void defaultcode() {
        System.out.println("Pc actual:" + pc);
        execute(pc);
        // Dejarlo vacío o con lógica adicional.
    }

    public void incrementcode() {
        Datum op;

        op = stack.pop(); // Sacamos el elemento de la pila.

        // Incrementamos el valor de la variable en el símbolo asociado.
        op.val += 1;

        // Metemos a la pila el nuevo valor.
        stack.push(op);

        System.out.println("Increment: " + op.symb.name + " -> " + op.symb.val);
    }

    public void decrementcode() {
        int AuxPc;
        Datum op1;

        AuxPc = pc;
        System.out.println("decrementcode - AuxPc inicial: " + AuxPc);

        // Obtener el valor de la variable
        execute(AuxPc + 1);
        op1 = stack.pop();
        System.out.println("decrementcode - Valor antes de decrementar: " + op1.val);

        // Decrementar el valor
        op1.val -= 1;
        System.out.println("decrementcode - Valor después de decrementar: " + op1.val);

        // Almacenar el nuevo valor de vuelta
        stack.push(op1);

        // Actualizar el contador de programa
        pc = AuxPc + 2;
        System.out.println("decrementcode - pc actualizado: " + pc);
    }

    public void continuecode() {
        int AuxPc, InicioBucle;

        AuxPc = pc;
        System.out.println("continuecode - AuxPc inicial: " + AuxPc);

        // Identificar el inicio del bucle actual
        InicioBucle = Prog[AuxPc].jump;
        System.out.println("continuecode - Saltando al inicio del bucle: " + InicioBucle);

        // Saltar al inicio del bucle (reevaluar la condición o actualizar)
        pc = InicioBucle;
        System.out.println("continuecode - pc actualizado para continuar el bucle: " + pc);
    }
}
