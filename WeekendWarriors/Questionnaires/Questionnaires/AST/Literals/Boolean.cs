﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Questionnaires.SemanticAnalysis.Messages;
using System.Diagnostics;

namespace Questionnaires.AST.Literals
{
    public class Boolean : IExpression
    {
        public Boolean(bool value)
        {
            this.Value = value;
        }

        public bool Value
        {
            get;
        }

        public QLType? CheckOperandTypes(List<QLType> parameters, SemanticAnalysis.QLContext context, List<SemanticAnalysis.Messages.Message> events)
        {
            Trace.Assert(parameters.Count == 0);
            return QLType.Bool;
        }        
    }
}
