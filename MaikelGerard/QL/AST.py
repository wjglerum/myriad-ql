from decimal import Decimal
from datetime import date

# create LiteralNodes and VarNode separate --> both are ExpressionNode.
# TODO: Remove print and eq, add to seperate traversal.
# TODO: Remove QuestionnaireAST, just make form the root.
# TODO: Replace 'is_boolean' etc. into double dispatch.
# TODO: Do we want to pass state in the visitor?
# TODO: question.name instead of question.name.val


class Node(object):
    def __init__(self, line=0, col=0):
        self.line = line
        self.col = col

    def assert_message(self, message):
        return "Node [{},{}]: {}".format(self.line, self.col, message)

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return True


class FormNode(Node):
    def __init__(self, name, form_body, line=0, col=0):
        super(FormNode, self).__init__(line, col)
        self.name = name
        self.form_block = form_body

    def accept(self, visitor, *args):
        self.form_block.accept(visitor, *args)

    def __eq__(self, other):
        return super(FormNode, self).__eq__(other) and \
               self.name == other.name and \
               self.form_block == other.form_block


class BlockNode(Node):
    def __init__(self, block_body, line=0, col=0):
        super(BlockNode, self).__init__(line, col)
        self.children = []

        for statement in block_body:
            self.children.append(statement)

    def accept(self, visitor, *args):
        for child in self.children:
            child.accept(visitor, *args)

    def __eq__(self, other):
        if not super(BlockNode, self).__eq__(other):
            return False

        for (child_self, child_other) in zip(self.children, other.children):
            # Use == as it is the only operator implemented for 'Node'.
            if not child_self == child_other:
                return False
        return True


class QuestionNode(Node):
    def __init__(self, question, name, var_type, line=0, col=0):
        super(QuestionNode, self).__init__(line, col)
        self.question = question
        self.name = name
        self.type = var_type

    def accept(self, visitor, *args):
        visitor.question_node(self, *args)

    def __eq__(self, other):
        return super(QuestionNode, self).__eq__(other) and \
               other.question == self.question and other.name == self.name and \
               other.type == self.type


class ComputedQuestionNode(QuestionNode):
    def __init__(self, question, name, var_type, expression, line=0, col=0):
        super(ComputedQuestionNode, self).__init__(
            question, name, var_type, line, col
        )
        self.expression = expression

    def accept(self, visitor, *args):
        visitor.comp_question_node(self, *args)

    def __eq__(self, other):
        return super(ComputedQuestionNode, self).__eq__(other) and \
               other.expression == self.expression


class IfNode(Node):
    def __init__(self, condition, if_block, line=0, col=0):
        super(IfNode, self).__init__(line, col)
        self.condition = condition
        self.if_block = if_block

    def accept(self, visitor, *args):
        visitor.if_node(self, *args)

    def __eq__(self, other):
        return super(IfNode, self).__eq__(other) and \
               other.condition == self.condition and \
               other.if_block == self.if_block


class IfElseNode(IfNode):
    def __init__(self, condition, if_block, else_block, line=0, col=0):
        super(IfElseNode, self).__init__(condition, if_block, line, col)
        self.else_block = else_block

    def accept(self, visitor, *args):
        visitor.if_else_node(self, *args)

    def __eq__(self, other):
        return super(IfElseNode, self).__eq__(other) and \
               other.else_block == self.else_block


class ExpressionNode(Node):
    def __init__(self, line=0, col=0):
        super(ExpressionNode, self).__init__(line, col)

    def accept(self, visitor, *args):
        return visitor.expr_node(self, *args)


class MonOpNode(ExpressionNode):
    def __init__(self, operator, expression, line, col):
        super(MonOpNode, self).__init__(line, col)
        self.operator = operator
        self.expression = expression

    def __eq__(self, other):
        return super(MonOpNode, self).__eq__(other) and \
               other.expression == self.expression


class NegNode(MonOpNode):
    def __init__(self, expression, line=0, col=0):
        super(NegNode, self).__init__("!", expression, line, col)

    def accept(self, visitor, *args):
        return visitor.neg_node(self, *args)


class MinNode(MonOpNode):
    def __init__(self, expression, line=0, col=0):
        super(MinNode, self).__init__("-", expression, line, col)

    def accept(self, visitor, *args):
        return visitor.min_node(self, *args)


class PlusNode(MonOpNode):
    def __init__(self, expression, line=0, col=0):
        super(PlusNode, self).__init__("+", expression, line, col)

    def accept(self, visitor, *args):
        return visitor.plus_node(self, *args)


class ArithmeticExprNode(ExpressionNode):
    def __init__(self, operator, left, right, line=0, col=0):
        super(ArithmeticExprNode, self).__init__(line, col)
        self.left = left
        self.operator = operator
        self.right = right

    def __eq__(self, other):
        return super(ArithmeticExprNode, self).__eq__(other) and \
               other.left == self.left and other.right == self.right


class AddNode(ArithmeticExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(AddNode, self).__init__("+", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.add_node(self, *args)


class SubNode(ArithmeticExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(SubNode, self).__init__("-", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.sub_node(self, *args)


class MulNode(ArithmeticExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(MulNode, self).__init__("*", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.mul_node(self, *args)


class DivNode(ArithmeticExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(DivNode, self).__init__("/", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.div_node(self, *args)


class ComparisonExprNode(Node):
    def __init__(self, operator, left, right, line=0, col=0):
        super(ComparisonExprNode, self).__init__(line, col)
        self.left = left
        self.operator = operator
        self.right = right

    def __eq__(self, other):
        return super(ComparisonExprNode, self).__eq__(other) and \
               other.left == self.left and other.right == self.right


class LTNode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(LTNode, self).__init__("<", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.lt_node(self, *args)


class LTENode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(LTENode, self).__init__("<=", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.lte_node(self, *args)


class GTNode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(GTNode, self).__init__(">", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.gt_node(self, *args)


class GTENode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(GTENode, self).__init__(">=", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.gte_node(self, *args)


class EqNode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(EqNode, self).__init__("==", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.eq_node(self, *args)


class NeqNode(ComparisonExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(NeqNode, self).__init__("!=", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.neq_node(self, *args)


class LogicalExprNode(ExpressionNode):
    def __init__(self, operator, left, right, line=0, col=0):
        super(LogicalExprNode, self).__init__(line, col)
        self.left = left
        self.operator = operator
        self.right = right

    def __eq__(self, other):
        return super(LogicalExprNode, self).__eq__(other) and \
               other.left == self.left and other.right == self.right


class AndNode(LogicalExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(AndNode, self).__init__("&&", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.and_node(self, *args)


class OrNode(LogicalExprNode):
    def __init__(self, left, right, line=0, col=0):
        super(OrNode, self).__init__("||", left, right, line, col)

    def accept(self, visitor, *args):
        return visitor.or_node(self, *args)


class TypeNode(Node):
    def __init__(self, type_name, line=0, col=0):
        super(TypeNode, self).__init__(line, col)
        self.name = type_name

        self.numeric = ["integer", "money", "decimal"]
        self.alphanumeric = self.numeric + ["string"]

    def is_numeric(self):
        return self.name in self.numeric

    def is_alphanumeric(self):
        return self.name in self.alphanumeric

    def is_boolean(self):
        return self.name == "boolean"

    def is_integer(self):
        return self.name == "integer"

    def is_money(self):
        return self.name == "money"

    def is_decimal(self):
        return self.name == "decimal"

    def is_string(self):
        return self.name == "string"

    def is_date(self):
        return self.name == "date"

    def __eq__(self, other):
        return super(TypeNode, self).__eq__(other) \
               and other.name == self.name


class BoolTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(BoolTypeNode, self).__init__("boolean", line, col)
        self.default = False

    def parse_value(self, value):
        return value

    def accept(self, visitor, *args):
        return visitor.bool_type_node(self, *args)


class IntTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(IntTypeNode, self).__init__("integer", line, col)
        self.default = Decimal("0")

    def parse_value(self, value):
        return Decimal(value)

    def accept(self, visitor, *args):
        return visitor.int_type_node(self, *args)


class MoneyTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(MoneyTypeNode, self).__init__("money", line, col)
        self.default = Decimal("0.00")

    def parse_value(self, value):
        return Decimal(value)

    def accept(self, visitor, *args):
        return visitor.money_type_node(self, *args)


class DecimalTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(DecimalTypeNode, self).__init__("decimal", line, col)
        self.default = Decimal("0.00")

    def parse_value(self, value):
        return Decimal(value)

    def accept(self, visitor, *args):
        return visitor.decimal_type_node(self, *args)


class StringTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(StringTypeNode, self).__init__("string", line, col)
        self.default = ""

    def parse_value(self, value):
        return value

    def accept(self, visitor, *args):
        return visitor.string_type_node(self, *args)


class DateTypeNode(TypeNode):
    def __init__(self, line=0, col=0):
        super(DateTypeNode, self).__init__("date", line, col)
        self.default = date(day=1, month=1, year=2000)

    def parse_value(self, value):
        return value

    def accept(self, visitor, *args):
        return visitor.date_type_node(self, *args)


class LiteralNode(ExpressionNode):
    def __init__(self, value, line=0, col=0):
        super(LiteralNode, self).__init__(line, col)
        self.val = value

    def accept(self, visitor, *args):
        return visitor.literal_node(self, *args)

    def __eq__(self, other):
        return super(LiteralNode, self).__eq__(other) and other.val == self.val


class VarNode(ExpressionNode):
    def __init__(self, name, line=0, col=0):
        super(ExpressionNode, self).__init__(line, col)
        self.name = name

    def accept(self, visitor, *args):
        return visitor.var_node(self, *args)

    def __eq__(self, other):
        return super(ExpressionNode, self).__eq__(other) and other.name == self.name


class StringNode(LiteralNode):
    def __init__(self, string, line=0, col=0):
        super(StringNode, self).__init__(string, line, col)

    def accept(self, visitor, *args):
        return visitor.string_node(self, *args)


class IntNode(LiteralNode):
    def __init__(self, integer, line=0, col=0):
        super(IntNode, self).__init__(integer, line, col)

    def accept(self, visitor, *args):
        return visitor.int_node(self, *args)


class BoolNode(LiteralNode):
    def __init__(self, boolean, line=0, col=0):
        super(BoolNode, self).__init__(boolean, line, col)

    def accept(self, visitor, *args):
        return visitor.bool_node(self, *args)


class MoneyNode(LiteralNode):
    def __init__(self, money, line=0, col=0):
        super(MoneyNode, self).__init__(money, line, col)

    def accept(self, visitor, *args):
        return visitor.decimal_node(self, *args)


class DecimalNode(LiteralNode):
    def __init__(self, decimal, line=0, col=0):
        super(DecimalNode, self).__init__(decimal, line, col)

    def accept(self, visitor, *args):
        return visitor.decimal_node(self, *args)


class DateNode(LiteralNode):
    def __init__(self, date_val, line=0, col=0):
        super(DateNode, self).__init__(date_val, line, col)

    def accept(self, visitor, *args):
        return visitor.date_node(self, *args)