import { BlockService } from '../../service/block/block.service';
import { Component, OnInit, Input } from '@angular/core';
import { Card } from '../../models/card.model';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  @Input() card: Card;

  constructor(private blockService: BlockService) { }

  ngOnInit(): void {
  }

  blockCard() {
    this.blockService.blockCard(this.card).subscribe(
      _ => this.card.blocked = true
    );
  }
}
