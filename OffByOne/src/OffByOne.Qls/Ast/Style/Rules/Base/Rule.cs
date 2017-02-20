﻿namespace OffByOne.Qls.Ast.Style.Rules.Base
{
    using System.Collections.Generic;

    using OffByOne.LanguageCore.Ast;
    using OffByOne.Qls.Ast.Style.Properties.Base;
    using OffByOne.Qls.Ast.Style.Widgets.Base;

    public class Rule : AstNode
    {
        public Rule(
            Widget widget,
            IEnumerable<Property> properties)
        {
            this.Widget = widget;
            this.Properties = properties;
        }

        public Widget Widget { get; private set; }

        public IEnumerable<Property> Properties { get; private set; }
    }
}