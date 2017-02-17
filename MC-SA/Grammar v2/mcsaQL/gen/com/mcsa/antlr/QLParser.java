// Generated from /Users/matt/Repos/myriad-ql/MC-SA/Grammar v2/mcsaQL/src/grammars/QL.g4 by ANTLR 4.6
package com.mcsa.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, ID=22, STRING=23, NUMBER=24, WHITESPACE=25, 
		COMMENT=26, OPEN_BRACKET=27, CLOSE_BRACKET=28, OPEN_PARENTH=29, CLOSE_PARENTH=30;
	public static final int
		RULE_start = 0, RULE_statementContent = 1, RULE_categorise = 2, RULE_ifCase = 3, 
		RULE_ifCaseArgs = 4, RULE_caseNewInput = 5, RULE_type = 6, RULE_mathaction = 7;
	public static final String[] ruleNames = {
		"start", "statementContent", "categorise", "ifCase", "ifCaseArgs", "caseNewInput", 
		"type", "mathaction"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'form'", "'if'", "'>'", "'<'", "'<='", "'>='", "'=='", "'AND'", 
		"'OR'", "':'", "'boolean'", "'integer'", "'double'", "'float'", "'string'", 
		"'money'", "'='", "'*'", "'/'", "'+'", "'-'", null, null, null, null, 
		null, "'{'", "'}'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "ID", "STRING", 
		"NUMBER", "WHITESPACE", "COMMENT", "OPEN_BRACKET", "CLOSE_BRACKET", "OPEN_PARENTH", 
		"CLOSE_PARENTH"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "QL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(QLParser.ID, 0); }
		public StatementContentContext statementContent() {
			return getRuleContext(StatementContentContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(T__0);
			setState(17);
			match(ID);
			setState(18);
			match(OPEN_BRACKET);
			setState(19);
			statementContent();
			setState(20);
			match(CLOSE_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContentContext extends ParserRuleContext {
		public List<CategoriseContext> categorise() {
			return getRuleContexts(CategoriseContext.class);
		}
		public CategoriseContext categorise(int i) {
			return getRuleContext(CategoriseContext.class,i);
		}
		public StatementContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterStatementContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitStatementContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitStatementContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContentContext statementContent() throws RecognitionException {
		StatementContentContext _localctx = new StatementContentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statementContent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << STRING) | (1L << NUMBER) | (1L << WHITESPACE) | (1L << COMMENT))) != 0)) {
				{
				{
				setState(22);
				categorise();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CategoriseContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(QLParser.STRING, 0); }
		public CaseNewInputContext caseNewInput() {
			return getRuleContext(CaseNewInputContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(QLParser.NUMBER, 0); }
		public TerminalNode WHITESPACE() { return getToken(QLParser.WHITESPACE, 0); }
		public TerminalNode COMMENT() { return getToken(QLParser.COMMENT, 0); }
		public TerminalNode OPEN_PARENTH() { return getToken(QLParser.OPEN_PARENTH, 0); }
		public IfCaseContext ifCase() {
			return getRuleContext(IfCaseContext.class,0);
		}
		public TerminalNode CLOSE_PARENTH() { return getToken(QLParser.CLOSE_PARENTH, 0); }
		public TerminalNode OPEN_BRACKET() { return getToken(QLParser.OPEN_BRACKET, 0); }
		public StatementContentContext statementContent() {
			return getRuleContext(StatementContentContext.class,0);
		}
		public TerminalNode CLOSE_BRACKET() { return getToken(QLParser.CLOSE_BRACKET, 0); }
		public CategoriseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_categorise; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterCategorise(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitCategorise(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitCategorise(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CategoriseContext categorise() throws RecognitionException {
		CategoriseContext _localctx = new CategoriseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_categorise);
		try {
			setState(41);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				match(STRING);
				setState(29);
				caseNewInput();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(30);
				match(NUMBER);
				}
				break;
			case WHITESPACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(31);
				match(WHITESPACE);
				}
				break;
			case COMMENT:
				enterOuterAlt(_localctx, 4);
				{
				setState(32);
				match(COMMENT);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 5);
				{
				setState(33);
				match(T__1);
				setState(34);
				match(OPEN_PARENTH);
				setState(35);
				ifCase(0);
				setState(36);
				match(CLOSE_PARENTH);
				setState(37);
				match(OPEN_BRACKET);
				setState(38);
				statementContent();
				setState(39);
				match(CLOSE_BRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfCaseContext extends ParserRuleContext {
		public List<IfCaseArgsContext> ifCaseArgs() {
			return getRuleContexts(IfCaseArgsContext.class);
		}
		public IfCaseArgsContext ifCaseArgs(int i) {
			return getRuleContext(IfCaseArgsContext.class,i);
		}
		public List<IfCaseContext> ifCase() {
			return getRuleContexts(IfCaseContext.class);
		}
		public IfCaseContext ifCase(int i) {
			return getRuleContext(IfCaseContext.class,i);
		}
		public IfCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterIfCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitIfCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitIfCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfCaseContext ifCase() throws RecognitionException {
		return ifCase(0);
	}

	private IfCaseContext ifCase(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		IfCaseContext _localctx = new IfCaseContext(_ctx, _parentState);
		IfCaseContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_ifCase, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(44);
				ifCaseArgs();
				}
				break;
			case 2:
				{
				setState(45);
				ifCaseArgs();
				setState(46);
				match(T__2);
				setState(47);
				ifCaseArgs();
				}
				break;
			case 3:
				{
				setState(49);
				ifCaseArgs();
				setState(50);
				match(T__3);
				setState(51);
				ifCaseArgs();
				}
				break;
			case 4:
				{
				setState(53);
				ifCaseArgs();
				setState(54);
				match(T__4);
				setState(55);
				ifCaseArgs();
				}
				break;
			case 5:
				{
				setState(57);
				ifCaseArgs();
				setState(58);
				match(T__5);
				setState(59);
				ifCaseArgs();
				}
				break;
			case 6:
				{
				setState(61);
				ifCaseArgs();
				setState(62);
				match(T__6);
				setState(63);
				ifCaseArgs();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(75);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(73);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new IfCaseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_ifCase);
						setState(67);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(68);
						match(T__7);
						setState(69);
						ifCase(3);
						}
						break;
					case 2:
						{
						_localctx = new IfCaseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_ifCase);
						setState(70);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(71);
						match(T__8);
						setState(72);
						ifCase(2);
						}
						break;
					}
					} 
				}
				setState(77);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IfCaseArgsContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(QLParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(QLParser.NUMBER, 0); }
		public IfCaseArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifCaseArgs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterIfCaseArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitIfCaseArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitIfCaseArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfCaseArgsContext ifCaseArgs() throws RecognitionException {
		IfCaseArgsContext _localctx = new IfCaseArgsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ifCaseArgs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseNewInputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(QLParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public CaseNewInputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseNewInput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterCaseNewInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitCaseNewInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitCaseNewInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseNewInputContext caseNewInput() throws RecognitionException {
		CaseNewInputContext _localctx = new CaseNewInputContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_caseNewInput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(ID);
			setState(81);
			match(T__9);
			setState(82);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public List<MathactionContext> mathaction() {
			return getRuleContexts(MathactionContext.class);
		}
		public MathactionContext mathaction(int i) {
			return getRuleContext(MathactionContext.class,i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_type);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				match(T__10);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(T__11);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				match(T__12);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				match(T__13);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
				match(T__14);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 6);
				{
				setState(89);
				match(T__15);
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16) {
					{
					{
					setState(90);
					match(T__16);
					setState(91);
					mathaction(0);
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MathactionContext extends ParserRuleContext {
		public Token op;
		public TerminalNode NUMBER() { return getToken(QLParser.NUMBER, 0); }
		public TerminalNode ID() { return getToken(QLParser.ID, 0); }
		public List<MathactionContext> mathaction() {
			return getRuleContexts(MathactionContext.class);
		}
		public MathactionContext mathaction(int i) {
			return getRuleContext(MathactionContext.class,i);
		}
		public MathactionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mathaction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).enterMathaction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QLListener ) ((QLListener)listener).exitMathaction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QLVisitor ) return ((QLVisitor<? extends T>)visitor).visitMathaction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MathactionContext mathaction() throws RecognitionException {
		return mathaction(0);
	}

	private MathactionContext mathaction(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MathactionContext _localctx = new MathactionContext(_ctx, _parentState);
		MathactionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_mathaction, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				setState(100);
				match(NUMBER);
				}
				break;
			case ID:
				{
				setState(101);
				match(ID);
				}
				break;
			case OPEN_PARENTH:
				{
				setState(102);
				match(OPEN_PARENTH);
				setState(103);
				mathaction(0);
				setState(104);
				match(CLOSE_PARENTH);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(116);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(114);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new MathactionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathaction);
						setState(108);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(109);
						((MathactionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__17 || _la==T__18) ) {
							((MathactionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(110);
						mathaction(6);
						}
						break;
					case 2:
						{
						_localctx = new MathactionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathaction);
						setState(111);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(112);
						((MathactionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__19 || _la==T__20) ) {
							((MathactionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(113);
						mathaction(5);
						}
						break;
					}
					} 
				}
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return ifCase_sempred((IfCaseContext)_localctx, predIndex);
		case 7:
			return mathaction_sempred((MathactionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean ifCase_sempred(IfCaseContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean mathaction_sempred(MathactionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3 z\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\3\7\3\32\n\3\f\3\16\3\35\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\5\4,\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5D\n\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\7\5L\n\5\f\5\16\5O\13\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\7\b_\n\b\f\b\16\bb\13\b\5\bd\n\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\5\tm\n\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tu\n\t\f\t\16\tx\13\t\3"+
		"\t\2\4\b\20\n\2\4\6\b\n\f\16\20\2\5\4\2\30\30\32\32\3\2\24\25\3\2\26\27"+
		"\u0087\2\22\3\2\2\2\4\33\3\2\2\2\6+\3\2\2\2\bC\3\2\2\2\nP\3\2\2\2\fR\3"+
		"\2\2\2\16c\3\2\2\2\20l\3\2\2\2\22\23\7\3\2\2\23\24\7\30\2\2\24\25\7\35"+
		"\2\2\25\26\5\4\3\2\26\27\7\36\2\2\27\3\3\2\2\2\30\32\5\6\4\2\31\30\3\2"+
		"\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\5\3\2\2\2\35\33\3\2"+
		"\2\2\36\37\7\31\2\2\37,\5\f\7\2 ,\7\32\2\2!,\7\33\2\2\",\7\34\2\2#$\7"+
		"\4\2\2$%\7\37\2\2%&\5\b\5\2&\'\7 \2\2\'(\7\35\2\2()\5\4\3\2)*\7\36\2\2"+
		"*,\3\2\2\2+\36\3\2\2\2+ \3\2\2\2+!\3\2\2\2+\"\3\2\2\2+#\3\2\2\2,\7\3\2"+
		"\2\2-.\b\5\1\2.D\5\n\6\2/\60\5\n\6\2\60\61\7\5\2\2\61\62\5\n\6\2\62D\3"+
		"\2\2\2\63\64\5\n\6\2\64\65\7\6\2\2\65\66\5\n\6\2\66D\3\2\2\2\678\5\n\6"+
		"\289\7\7\2\29:\5\n\6\2:D\3\2\2\2;<\5\n\6\2<=\7\b\2\2=>\5\n\6\2>D\3\2\2"+
		"\2?@\5\n\6\2@A\7\t\2\2AB\5\n\6\2BD\3\2\2\2C-\3\2\2\2C/\3\2\2\2C\63\3\2"+
		"\2\2C\67\3\2\2\2C;\3\2\2\2C?\3\2\2\2DM\3\2\2\2EF\f\4\2\2FG\7\n\2\2GL\5"+
		"\b\5\5HI\f\3\2\2IJ\7\13\2\2JL\5\b\5\4KE\3\2\2\2KH\3\2\2\2LO\3\2\2\2MK"+
		"\3\2\2\2MN\3\2\2\2N\t\3\2\2\2OM\3\2\2\2PQ\t\2\2\2Q\13\3\2\2\2RS\7\30\2"+
		"\2ST\7\f\2\2TU\5\16\b\2U\r\3\2\2\2Vd\7\r\2\2Wd\7\16\2\2Xd\7\17\2\2Yd\7"+
		"\20\2\2Zd\7\21\2\2[`\7\22\2\2\\]\7\23\2\2]_\5\20\t\2^\\\3\2\2\2_b\3\2"+
		"\2\2`^\3\2\2\2`a\3\2\2\2ad\3\2\2\2b`\3\2\2\2cV\3\2\2\2cW\3\2\2\2cX\3\2"+
		"\2\2cY\3\2\2\2cZ\3\2\2\2c[\3\2\2\2d\17\3\2\2\2ef\b\t\1\2fm\7\32\2\2gm"+
		"\7\30\2\2hi\7\37\2\2ij\5\20\t\2jk\7 \2\2km\3\2\2\2le\3\2\2\2lg\3\2\2\2"+
		"lh\3\2\2\2mv\3\2\2\2no\f\7\2\2op\t\3\2\2pu\5\20\t\bqr\f\6\2\2rs\t\4\2"+
		"\2su\5\20\t\7tn\3\2\2\2tq\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\21\3"+
		"\2\2\2xv\3\2\2\2\f\33+CKM`cltv";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}