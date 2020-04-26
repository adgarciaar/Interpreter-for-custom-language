grammar Car;

@parser::header {
	import java.util.Map;
	import java.util.HashMap;
	import java.util.List;
	import java.util.ArrayList;
	import co.edu.javeriana.car.ast.*;
}

@parser::members {
	
	private Car car;
	
	public CarParser(TokenStream input, Car car) {
	    this(input);
	    this.car = car;
	}

}

program: 
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
		Map<String, Object> symbolTable = new HashMap<String, Object>();
	}
	(sentence {body.add($sentence.node);} )*
	{
		for(ASTNode n: body){
			n.execute( symbolTable );
		}
	}
	;
	

sentence returns [ASTNode node]: 
	print { $node = $print.node; } 
	| conditional { $node = $conditional.node; }
	| var_decl { $node = $var_decl.node; }
	| var_assign { $node = $var_assign.node; }
	| turn { $node = $turn.node; }
	| movement { $node = $movement.node; }
	| set_color { $node = $set_color.node; }
	| while_cycle { $node = $while_cycle.node; }
	| function { $node = $function.node; }
	| execute_function { $node = $execute_function.node; }
	| sentence_retorno { $node = $sentence_retorno.node; }
	;
	

movement returns [ASTNode node]: 
	forward_movement { $node = $forward_movement.node; }
	| backwards_movement { $node = $backwards_movement.node; };
	
forward_movement returns [ASTNode node]: 
	MOVE_FW expression
	{ $node = new MoveForward($expression.node, this.car); };

backwards_movement returns [ASTNode node]: 
	MOVE_BK expression
	{ $node = new MoveBackwards($expression.node, this.car); };
	

turn returns [ASTNode node]: 
	turn_right { $node = $turn_right.node; }
	| turn_left { $node = $turn_left.node; } ;

turn_right returns [ASTNode node]: 
	TURN_RT expression
	{ $node = new TurnRight($expression.node, this.car); };

turn_left returns [ASTNode node]: 
	TURN_LT expression
	{ $node = new TurnLeft($expression.node, this.car); };
	

var_decl returns [ASTNode node]: 
	DECLARE_VAR ID { $node = new VarDecl($ID.text); }
	| DECLARE_VAR ID ASSIGN_VAR expression { $node = new VarDecl($ID.text, $expression.node); };

var_assign returns [ASTNode node]: 
	ID ASSIGN_VAR expression
	{ $node = new VarAssign($ID.text, $expression.node); };
	

conditional returns [ASTNode node]:
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
		List<ASTNode> elseBody = new ArrayList<ASTNode>();
	}
	IF PAR_OPEN expression PAR_CLOSE
	(s1 = sentence { body.add ($s1.node); } )* 
	(ELSE
	(s2 = sentence { elseBody.add ($s2.node); } )*
	)? 
	ENDIF
	{
		$node = new Conditional( $expression.node, body, elseBody );
	}
	;
	
while_cycle returns [ASTNode node]:
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
	}
	WHILE PAR_OPEN expression PAR_CLOSE
	(s1 = sentence { body.add ($s1.node); } )*
	END_WHILE
	{
		$node = new While( $expression.node, body );
	}
	;
	

print returns [ASTNode node]: 
	ECHO expression
	{ $node = new Print( $expression.node ); };
	

set_color returns [ASTNode node]: 
	SET_RGBA e1 = expression COMMA e2 = expression
	{ $node = new SetColor( $e1.node, $e2.node, this.car ); };
	
	
sentence_retorno returns [ASTNode node]:
	RETURN expression
	{ $node = new FunctionReturn($expression.node); }
	;	
	
expression returns [ASTNode node]:
	t1 = and_operator { $node = $t1.node; }
	(OR t2 = and_operator { $node = new Or($node, $t2.node); } )*
	;
	
and_operator returns [ASTNode node]:
	t1 = not_operator { $node = $t1.node; }
	(AND t2 = not_operator { $node = new And($node,$t2.node); } )*	
	;
	
not_operator returns [ASTNode node]:
	t1 = comparison { $node = $t1.node; }
	| NOT t2 = comparison { $node = new Not($t2.node); };		

comparison returns [ASTNode node]:
	t1 = plus_minus { $node = $t1.node; }
	( 
		EQ t2 = plus_minus { $node = new EqualThan($node, $t2.node); }
		| NEQ t2 = plus_minus { $node = new NotEqualThan($node, $t2.node); }
		| GT t2 = plus_minus { $node = new GreaterThan($node, $t2.node); }
		| LT t2 = plus_minus { $node = new LessThan($node, $t2.node); }
		| GEQ t2 = plus_minus { $node = new GreaterEqualThan($node, $t2.node); }
		| LEQ t2 = plus_minus { $node = new LessEqualThan($node, $t2.node); }
	)*
	;	
	
plus_minus returns [ASTNode node]:
	t1 = mult_div { $node = $t1.node; } 
	( 
		PLUS t2 = mult_div { $node = new Addition($node,$t2.node); }
		| MINUS t2 = mult_div { $node = new Subtraction($node,$t2.node); } 
	)*;
	
mult_div returns [ASTNode node]:
	t1 = term { $node = $t1.node; }
	( 
		MULT t2 = term { $node = new Multiplication($node, $t2.node); } 
		| DIV t2 = term { $node = new Division($node, $t2.node); }
	)*;
	
	
term returns [ASTNode node]:
	DECIMAL_CONSTANT { $node = new Constant ( Float.parseFloat($DECIMAL_CONSTANT.text) ); }
	| MINUS expression { $node = new NegativeExpression($expression.node); }
	| PLUS expression { $node = new PositiveExpression($expression.node); } 
	| BOOLEAN_CONSTANT { $node = new Constant (Boolean.parseBoolean( $BOOLEAN_CONSTANT.text )); }
	| COLOR { $node = new Constant ( $COLOR.text ); }
	| STRING_CONSTANT { $node = new Constant( $STRING_CONSTANT.text.substring( 1, $STRING_CONSTANT.text.length()-1 ) ); }
	| ID { $node = new VarRef($ID.text); }
	| execute_function { $node = $execute_function.node; }
	| PAR_OPEN expression { $node = $expression.node; } PAR_CLOSE
	;
	
function returns [ASTNode node]:
	DECLARE_PROC t1 = ID PAR_OPEN
	{
		List<String> parameters = new ArrayList<String>();
		List<ASTNode> body = new ArrayList<ASTNode>();	
	}	
	(
		t2 = ID { parameters.add($t2.text); }
		( COMMA t3 = ID { parameters.add($t3.text); } )*	
	)?
	PAR_CLOSE 
	( s1 = sentence { body.add ($s1.node); } )*
	END_PROC
	{
		$node = new Function($t1.text, parameters, body);
	};
	
execute_function returns [ASTNode node]:
	{
		List<ASTNode> arguments = new ArrayList<ASTNode>();	
	}
	ID PAR_OPEN 
	(
		t1 = expression{ arguments.add($t1.node); } 
		( COMMA t2 = expression{ arguments.add($t2.node); } )*
	)?
	PAR_CLOSE
	{
		$node = new ExecuteFunction($ID.text, arguments);
	};
	
//---------------------------------TOKENS---------------------------------
	
//PALABRAS CLAVE

//Comandos básicos

MOVE_FW: 'move_fw'; 
MOVE_BK: 'move_bk'; 
TURN_LT: 'turn_lt'; 
TURN_RT: 'turn_rt'; 
SET_RGBA: 'set_rgba';

//Declaración de variables y funciones

DECLARE_VAR: 'def_var';
DECLARE_PROC: 'proc';
END_PROC: 'end';

//Condicionales y ciclos

IF: 'if';
ELSE: 'else';
ENDIF: 'endif';
WHILE: 'while';
END_WHILE: 'endwhile';

//Impresión por pantalla

ECHO: 'echo';

//Retorno por parte de funciones

RETURN: 'return';


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

NOT: 'not';
AND: 'and';
OR: 'or';


//CONSTANTES

COLOR: '#'[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9];
//TRANSPARENCY_CONSTANT: ('0.'[0-9]+)|'0'|'1.0'|'1';
DECIMAL_CONSTANT: ([0-9]+'.'[0-9]+)|[0-9]+;
STRING_CONSTANT: '"'.+?'"';
BOOLEAN_CONSTANT: 'true'|'false';

//SÍMBOLOS DE PUNTUACIÓN

PAR_OPEN: '(';
PAR_CLOSE: ')';
COMMA: ',';

//IDENTIFICADORES

ID: ([a-zA-Z][a-zA-Z0-9_]*)|([a-zA-Z_][a-zA-Z0-9_]+);

//SEPARADORES QUE NO SE CONSIDERAN

WS: [ \t\r\n]+ -> skip;
