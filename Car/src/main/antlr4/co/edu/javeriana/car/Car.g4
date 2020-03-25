grammar Car;

@parser::header {
	import java.util.Map;
	import java.util.HashMap;
}

@parser::members {
	
	private Car car;
	
	public CarParser(TokenStream input, Car car) {
	    this(input);
	    this.car = car;
	}
	
	Map<String, Object> symbolTable = new HashMap<String, Object>();
}

program: sentence*;

sentence: movement | turn | set_color | var_decl | var_assign | print;


movement: forward_movement | backwards_movement;

turn: turn_right | turn_left;

var_decl: DECLARE_VAR NAME_VAR
	{ symbolTable.put( $NAME_VAR.text, null); };

var_assign: NAME_VAR ASSIGN_VAR decimal_number
	{ symbolTable.put( $NAME_VAR.text, $decimal_number.value); };
	
print: print_text | print_expression;

print_text: ECHO ptext
	{ System.out.println( $ptext.value ); };
	
print_expression: ECHO expression
	{ System.out.println( $expression.value ); };
	
expression returns [Object value]:
	t1 = factor { $value = (float)$t1.value; } 
	( (PLUS t2 = factor { $value = (float)$value + (float)$t2.value; } )*
	| (MINUS t2 = factor { $value = (float)$value - (float)$t2.value; } )* );
	
factor returns [Object value]:
	t1 = term { $value = (float)$t1.value; } 
	( (MULT t2 = term { $value = (float)$value * (float)$t2.value; } )*
	| (DIV t2 = term { $value = (float)$value / (float)$t2.value; } )* );
	
term returns [Object value]:
	decimal_number { $value = $decimal_number.value; }
	| MINUS decimal_number { $value = -$decimal_number.value; }
	| NAME_VAR { $value = symbolTable.get($NAME_VAR.text); }
	| PAR_OPEN expression PAR_CLOSE;
	
ptext returns [String value]:
	str_constant { $value =  $str_constant.value.substring( 1, $str_constant.value.length()-1 ); }
	|
	NAME_VAR { $value = String.valueOf( symbolTable.get($NAME_VAR.text) ); }
	;

set_color: SET_RGBA html_color COMMA transparency_number
	{ 
		this.car.color( 
			//red
			(float)Integer.valueOf( $html_color.value.substring( 1, 3 ), 16 ),
			//green
			(float)Integer.valueOf( $html_color.value.substring( 3, 5 ), 16 ),
			//blue
			(float)Integer.valueOf( $html_color.value.substring( 5, 7 ), 16 ),
			//alpha
			$transparency_number.value
		);
	};
	
	
forward_movement: MOVE_FW decimal_number
	{ this.car.forward($decimal_number.value); };

backwards_movement: MOVE_BK decimal_number
	{ this.car.backwards($decimal_number.value); };
	
	
turn_right: TURN_RT decimal_number
	{ this.car.right($decimal_number.value); };

turn_left: TURN_LT decimal_number
	{ this.car.left($decimal_number.value); };
	
	
html_color returns [String value]:
	COLOR { $value = $COLOR.text; };
	
decimal_number returns [Float value]:
	DECIMAL_CONSTANT { $value = Float.parseFloat($DECIMAL_CONSTANT.text); }
	|
	MINUS DECIMAL_CONSTANT { $value = -Float.parseFloat($DECIMAL_CONSTANT.text); } ;
	
transparency_number returns [Float value]:
	TRANSPARENCY_CONSTANT { $value = 255*Float.parseFloat($TRANSPARENCY_CONSTANT.text); };

boolean_constant returns [Boolean value]:
	BOOLEAN_CONSTANT { $value = Boolean.parseBoolean( $BOOLEAN_CONSTANT.text ); };
	
str_constant returns [String value]:
	STRING_CONSTANT { $value =  $STRING_CONSTANT.text; };
	
	
//---------------------------------TOKENS---------------------------------
	
//PALABRAS CLAVE

//Comandos básicos

MOVE_FW: 'move_fw'; 
MOVE_BK: 'move_bk'; 
TURN_LT: 'turn_lt'; 
TURN_RT: 'turn_rt'; 
SET_RGBA: 'set_rgba';

//Declaración de variables y procedimiento

DECLARE_VAR: 'def_var';
DECLARE_PROC: 'proc';

//Condicionales y ciclos

IF: 'if';
ELSE: 'else';
ENDIF: 'endif';
WHILE: 'while';
END_WHILE: 'endwhile';

//Impresión por pantalla

ECHO: 'echo';


//OPERADORES

//Aritméticos

PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';

//Comparadores

GT: '>';
LT: '<';
GEQ: '>=';
LEQ: '<=';
EQ: '=';
NEQ: '<>';
ASSIGN_VAR: ':=';

//Lógicos

AND: 'and';
OR: 'or';
NOT: 'not';


//IDENTIFICADORES

NAME_VAR: [a-zA-Z][a-zA-Z0-9]*;
NAME_FUNCTION: [a-zA-Z][a-zA-Z0-9]*;

//CONSTANTES

COLOR: '#'[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9];
TRANSPARENCY_CONSTANT: ('0.'[0-9]+)|'0'|'1.0'|'1';
DECIMAL_CONSTANT: ([0-9]+'.'[0-9]+)|[0-9]+;
STRING_CONSTANT: '"'.+?'"';
BOOLEAN_CONSTANT: 'true'|'false';

//SÍMBOLOS DE PUNTUACIÓN

PAR_OPEN: '(';
PAR_CLOSE: ')';
COMMA: ',';

//SEPARADORES QUE NO SE CONSIDERAN

WS: [ \t\r\n]+ -> skip;
