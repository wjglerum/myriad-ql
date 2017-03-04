﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Questionnaires.SemanticAnalysis.Messages;
using System.Diagnostics;

namespace Questionnaires.AST.Literals
{
    public class String : IExpression
    {
        public String(string value)
        {
            this.Value = value;
        }

        public string Value
        {
            get;
        }

        public QLType? CheckOperandTypes(List<QLType> parameters, SemanticAnalysis.QLContext context, List<SemanticAnalysis.Messages.Message> events)
        {
            Trace.Assert(parameters.Count == 0);

            return QLType.String;
        }
    }
}
