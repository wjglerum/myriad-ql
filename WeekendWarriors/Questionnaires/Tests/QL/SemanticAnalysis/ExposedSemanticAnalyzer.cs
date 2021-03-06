﻿using Questionnaires.ErrorHandling;
using Questionnaires.QL.SemanticAnalysis;

namespace Tests.QL.SemanticAnalysis
{
    /* Small helper class to expose the protected functions of the semantic alayzer that allow is to analyze nodes under the form. This has value for the
     * unit tests since we are unable to uniquely verify certain properties at the form level */
    public class ExposedSemanticAnalyzer : SemanticAnalyzer
    {
        public ExposedSemanticAnalyzer(Result result) : base(result)
        {

        }

        public new void AnalyzeAstNode(Questionnaires.QL.AST.INode node)
        {
            base.AnalyzeAstNode(node);
        }
    }
}
