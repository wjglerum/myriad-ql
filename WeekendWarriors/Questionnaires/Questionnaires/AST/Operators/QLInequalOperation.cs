﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Questionnaires.AST.Operators
{
    public class QLInequalOperation : QLEqualityOperation
    {
        public QLInequalOperation(IQLExpression lhs, IQLExpression rhs) : base(lhs, QLBinaryOperator.Inequal, rhs)
        {
        }
    }
}